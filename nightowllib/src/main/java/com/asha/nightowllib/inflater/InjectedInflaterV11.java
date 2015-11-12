package com.asha.nightowllib.inflater;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class InjectedInflaterV11 extends InjectedInflaterBase {

    private static final String TAG = "InjectedInflaterV11";
    private static Method sSetPrivateFactoryMethod;
    private static Field sPrivateFactoryField;

    static {
        try {
            sSetPrivateFactoryMethod = LayoutInflater.class.getDeclaredMethod("setPrivateFactory",Factory2.class);
            sSetPrivateFactoryMethod.setAccessible(true);
            sPrivateFactoryField = LayoutInflater.class.getDeclaredField("mPrivateFactory");
            sPrivateFactoryField.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    protected InjectedInflaterV11(LayoutInflater original, Context newContext) {
        super(original, newContext);
        installPrivateFactory();
    }

    private void installPrivateFactory(){
        try {
            Factory2 originPrivateFactory = (Factory2) sPrivateFactoryField.get(this);
            installPrivateFactory(originPrivateFactory);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void installPrivateFactory(Factory2 originPrivateFactory){
        try {
            // check origin private Factory is not null.
            if ( originPrivateFactory == null ) return;
            // already set
            if ( originPrivateFactory instanceof PrivateFactoryWrapperImpl ) return;
            // wrap PrivateFactory
            Factory2 privateFactory = PrivateFactoryWrapperImpl.wrap(originPrivateFactory, this);
            // replace it.
            sPrivateFactoryField.set(this, privateFactory);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setFactory(Factory factory) {
        if ( factory == null ) return;
        if ( getFactory() == null ){
            super.setFactory(FactoryWrapperImpl.wrap(factory));
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void setFactory2(Factory2 factory) {
        if ( factory == null ) return;
        if ( getFactory2() == null ){
            super.setFactory(Factory2WrapperImpl.wrap(factory));
        }
    }

    public void setPrivateFactory(Factory2 factory2){
        Log.e(TAG, "warning=== setPrivateFactory");
        try {
            Factory2 privateFactory = (Factory2) sPrivateFactoryField.get(this);
            if ( privateFactory != null
                    &&  privateFactory instanceof PrivateFactoryWrapperImpl ){
                // if it is FactoryWrapper.PrivateFactoryWrapperImpl
                // get inner factory and
                // set to super class
                privateFactory = ((PrivateFactoryWrapperImpl) privateFactory)
                        .getCoreFactory();
                sPrivateFactoryField.set(this, privateFactory);
            }
            // super.setPrivateFactory
            sSetPrivateFactoryMethod.invoke(this, factory2);
            installPrivateFactory(factory2);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /********************************************************************************
     *
     * FactoryWrapperImpl
     *
     * ******************************************************************************/
    public static class FactoryWrapperImpl implements LayoutInflater.Factory {
        private LayoutInflater.Factory mFactory;
        private FactoryWrapperImpl(LayoutInflater.Factory factory) {
            mFactory = factory;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            InjectedInflaterBase.handleOnCreateView(v, name, attrs);
            return v;
        }
        public static LayoutInflater.Factory wrap(LayoutInflater.Factory factory){
            return new FactoryWrapperImpl(factory);
        }
    }

    /********************************************************************************
     *
     * Factory2WrapperImpl
     *
     * ******************************************************************************/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class Factory2WrapperImpl implements LayoutInflater.Factory2 {
        private LayoutInflater.Factory2 mFactory;

        private Factory2WrapperImpl(LayoutInflater.Factory2 factory) {
            mFactory = factory;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            InjectedInflaterBase.handleOnCreateView(v, name, attrs);
            return v;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            InjectedInflaterBase.handleOnCreateView(v, name, attrs);
            return v;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public static LayoutInflater.Factory2 wrap(LayoutInflater.Factory2 factory){
            return new Factory2WrapperImpl(factory);
        }
    }

    /********************************************************************************
     *
     * PrivateFactoryWrapperImpl
     *
     * ******************************************************************************/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class PrivateFactoryWrapperImpl extends LastChance2Inflater implements LayoutInflater.Factory2 {
        private LayoutInflater.Factory2 mFactory;

        private PrivateFactoryWrapperImpl(LayoutInflater.Factory2 factory, InjectedInflaterBase inflater) {
            super(inflater);
            mFactory = factory;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            if ( v == null ) v = lastChance2CreateView(null, name, context, attrs);
            InjectedInflaterBase.handleOnCreateView(v, name, attrs);
            return v;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View v = mFactory.onCreateView(name,context,attrs);
            if ( v == null ) v = lastChance2CreateView(parent, name, context, attrs);
            InjectedInflaterBase.handleOnCreateView(v, name, attrs);
            return v;
        }

        public LayoutInflater.Factory2 getCoreFactory() {
            return mFactory;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public static LayoutInflater.Factory2 wrap(LayoutInflater.Factory2 factory
                , InjectedInflaterBase inflater){
            return new PrivateFactoryWrapperImpl(factory,inflater);
        }
    }
}
