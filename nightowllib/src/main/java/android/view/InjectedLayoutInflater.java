package android.view;

import android.content.Context;
import android.util.AttributeSet;

import com.asha.nightowllib.NightOwl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class InjectedLayoutInflater extends LayoutInflater {

    Method mSetPrivateFactoryMethod;
    Field mPrivateFactoryField;
    Field mConstructorArgsField;
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app."
    };

    private static final String TAG = "InjectLayoutInflater";

    public static LayoutInflater newInstance(LayoutInflater original, Context newContext) {
        return new InjectedLayoutInflater(original,newContext);
    }

    private InjectedLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        init();
        installPrivateFactory();
    }

    private void init() {
        try {
            mSetPrivateFactoryMethod = LayoutInflater.class.getDeclaredMethod("setPrivateFactory",Factory2.class);
            mSetPrivateFactoryMethod.setAccessible(true);
            mPrivateFactoryField = LayoutInflater.class.getDeclaredField("mPrivateFactory");
            mPrivateFactoryField.setAccessible(true);
            mConstructorArgsField = LayoutInflater.class.getDeclaredField("mConstructorArgs");
            mConstructorArgsField.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void installPrivateFactory(){
        try {
            Factory2 privateFactory = (Factory2) mPrivateFactoryField.get(this);
            installPrivateFactoryInner(privateFactory);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void installPrivateFactoryInner(Factory2 originPrviateFactory){
        try {
            // check originPrviateFactory is not null.
            if ( originPrviateFactory == null ) return;
            // already set
            if ( originPrviateFactory instanceof FactoryWrapper.PrivateFactoryWrapperImpl ) return;
            // wrap PrivateFactory
            Factory2 privateFactory = FactoryWrapper.wrapPrivate(originPrviateFactory,this);
            // replace it.
            mPrivateFactoryField.set(this, privateFactory);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setPrivateFactory(Factory2 factory2){
        try {
            Factory2 privateFactory = (Factory2) mPrivateFactoryField.get(this);
            if ( privateFactory != null
                    &&  privateFactory instanceof FactoryWrapper.PrivateFactoryWrapperImpl ){
                // if it is FactoryWrapper.PrivateFactoryWrapperImpl
                // get inner factory and
                // set to super class
                privateFactory = ((FactoryWrapper.PrivateFactoryWrapperImpl) privateFactory)
                        .getCoreFactory();
                mPrivateFactoryField.set(this,privateFactory);
            }
            // super.setPrivateFactory
            mSetPrivateFactoryMethod.invoke(this, factory2);
            installPrivateFactoryInner(factory2);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setFactory(Factory factory) {
        if ( factory == null ) return;
        if ( getFactory() == null ){
            super.setFactory(FactoryWrapper.wrap(factory));
        }

    }

    @Override
    public void setFactory2(Factory2 factory) {
        if ( factory == null ) return;
        if ( getFactory2() == null ){
            super.setFactory(FactoryWrapper.wrapF2(factory));
        }
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new InjectedLayoutInflater(this,newContext);
    }

    @Override protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        View v = null;
        for (String prefix : sClassPrefixList) {
            try {
                v = createView(name, prefix, attrs);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if ( v == null ){
            v = super.onCreateView(name, attrs);
        }
        handleOnCreateView(v,name,attrs);
        return v;
    }

    @Override
    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        View v = super.onCreateView(parent, name, attrs);
        //Log.d(TAG,"onCreateView2:" + v);
        handleOnCreateView(v,name,attrs);
        return v;
    }

    public Object[] getConstructorArgs(){
        try {
            return (Object[]) mConstructorArgsField.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void handleOnCreateView(View view,String name,AttributeSet attrs){
        if ( view == null ) return;
        NightOwl.handleViewCreated(view,attrs);
    }
}
