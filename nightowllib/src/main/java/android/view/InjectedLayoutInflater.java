package android.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.asha.nightowllib.NightOwl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class InjectedLayoutInflater extends LayoutInflater {

    Field mFactorySetField;
    Method mSetPrivateFactoryMethod;
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
    }

    public static class FactoryWrapper implements Factory {
        private Factory mFactory;
        public static Factory wrap(Factory factory){
            return new FactoryWrapper(factory);
        }
        private FactoryWrapper(Factory factory) {
            mFactory = factory;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            //Log.e(TAG, "onCreateView FactoryWrapper:" + name + "," + v);
            handleOnCreateView(v,name,attrs);
            return v;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class FactoryWrapper2 implements Factory2 {
        private Factory2 mFactory;
        public static Factory wrap(Factory2 factory){
            return new FactoryWrapper(factory);
        }
        private FactoryWrapper2(Factory2 factory) {
            mFactory = factory;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            //Log.e(TAG, "onCreateView FactoryWrapper2:" + name + "," + v);
            handleOnCreateView(v,name,attrs);
            return v;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            //Log.e(TAG, "onCreateView FactoryWrapper2:" + name + "," + v);
            handleOnCreateView(v,name,attrs);
            return v;
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
            super.setFactory(FactoryWrapper2.wrap(factory));
        }
    }

    private void init() {
        try {
            mFactorySetField = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySetField.setAccessible(true);

            mSetPrivateFactoryMethod = LayoutInflater.class.getDeclaredMethod("setPrivateFactory",Factory2.class);
            mSetPrivateFactoryMethod.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
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

    private static void handleOnCreateView(View view,String name,AttributeSet attrs){
        if ( view == null ) return;
        NightOwl.handleOnCreateView(view,attrs);
    }


}
