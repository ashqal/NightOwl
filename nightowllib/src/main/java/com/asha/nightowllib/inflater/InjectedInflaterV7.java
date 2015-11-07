package com.asha.nightowllib.inflater;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class InjectedInflaterV7 extends InjectedInflaterBase {
    private static Field sFactoryField;
    private static boolean sCheckFactoryField;

    protected InjectedInflaterV7(LayoutInflater original, Context newContext) {
        super(original, newContext);
        installFactory();
    }

    private void installFactory(){
        Factory factory = getFactory();
        if ( factory == null ) return;
        if ( factory instanceof FactoryWrapperImpl ) return;
        Factory newFactory = FactoryWrapperImpl.wrap(this,factory);
        ensureMethod();
        try {
            sFactoryField.set(this,newFactory);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setFactory(Factory factory) {
        if ( factory == null ) return;
        if ( getFactory() == null ){
            super.setFactory(FactoryWrapperImpl.wrap(this,factory));
        }
    }

    private static void ensureMethod(){
        if ( sCheckFactoryField ) return;
        try {
            sFactoryField = LayoutInflater.class.getDeclaredField("mFactory");
            sFactoryField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        sCheckFactoryField = true;
    }

    /********************************************************************************
     *
     * FactoryWrapperImpl
     *
     * ******************************************************************************/
    public static class FactoryWrapperImpl extends LastChance2Inflater implements LayoutInflater.Factory {
        private LayoutInflater.Factory mFactory;
        private FactoryWrapperImpl(InjectedInflaterBase inflater, LayoutInflater.Factory factory) {
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
        public static LayoutInflater.Factory wrap(InjectedInflaterBase inflater, LayoutInflater.Factory factory){
            return new FactoryWrapperImpl(inflater,factory);
        }
    }

}
