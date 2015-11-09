package com.asha.nightowllib;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.asha.nightowllib.handler.ISkinHandler;
import com.asha.nightowllib.observer.OwlObservable;
import com.asha.nightowllib.paint.ColorBox;

import java.lang.reflect.Field;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class NightOwlUtil {
    public static final int NIGHT_OWL_VIEW_TAG = (2 << 24) | (1 << 23);
    private static final String TAG = "NightOwlUtil";

    public static void checkNonNull(Object obj,String msg){
        if ( obj == null ) throw new NullPointerException(msg);
    }

    public static boolean checkHandler(ISkinHandler handler,View view){
        if ( handler == null ){
            Log.e(TAG, "Can't find handler of clz:" + view.getClass().getName());
            return false;
        }
        return true;
    }

    public static boolean checkViewCollected(View view){
        return view.getTag( NIGHT_OWL_VIEW_TAG ) != null;
    }

    public static void insertSkinBox(@NonNull View view, ColorBox box){
        view.setTag(NIGHT_OWL_VIEW_TAG, box);
    }

    public static ColorBox obtainSkinBox(@NonNull View view){
        Object box = view.getTag(NIGHT_OWL_VIEW_TAG);
        checkNonNull(box,"wtf, it can't be null.");
        if ( box instanceof ColorBox ){
            return (ColorBox) box;
        } else {
            Log.e(TAG, "wtf, NIGHT_OWL_VIEW_TAG had been used by someone else.");
        }
        return null;
    }

    public static void insertObservable(@NonNull View view, OwlObservable owlObservable){
        view.setTag(NIGHT_OWL_VIEW_TAG + 1,owlObservable);
    }

    public static OwlObservable obtainObservable(@NonNull View view){
        Object observable = view.getTag(NIGHT_OWL_VIEW_TAG + 1);
        if ( observable != null && observable instanceof OwlObservable ){
            return (OwlObservable) observable;
        }
        return null;
    }

    public static int getStaticFieldIntSafely(Field field){
        try {
            return field.getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int[] getStaticFieldIntArraySafely(Field field){
        try {
            return (int[]) field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T newInstanceSafely(Class<T> clz){
        try {
            return clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void injectLayoutInflater(LayoutInflater layoutInflater, Object src,Class clz, String name){
        try {
            Field field = clz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(src, layoutInflater);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


}
