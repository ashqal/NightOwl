package com.asha.nightowllib;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.ContextThemeWrapper;
import android.view.InjectLayoutInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.asha.nightowllib.styleable.IStyleableHandler;

import java.lang.reflect.Field;
import java.util.HashMap;

import static com.asha.nightowllib.NightOwlUtil.checkNonNull;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class NightOwl {
    private static final String TAG = "NightOwl";
    private static NightOwl sInstance;

    HashMap<Class<? extends View>,IStyleableHandler> mHandlers;
    private static HashMap<Class<? extends View>,Class<IStyleableHandler>> sStyleableTable = new HashMap<>();
    private NightOwl(){
        mHandlers = new HashMap<>();
    }

    public static void inject(Activity activity){
        Window window = activity.getWindow();
        LayoutInflater layoutInflater = window.getLayoutInflater();
        InjectLayoutInflater injectLayoutInflater = new InjectLayoutInflater(layoutInflater,activity);
        injectLayoutInflater(injectLayoutInflater
                ,activity.getWindow()
                ,activity.getWindow().getClass()
                ,"mLayoutInflater");

        InjectLayoutInflater injectLayoutInflater2 = new InjectLayoutInflater(layoutInflater,activity);
        injectLayoutInflater(injectLayoutInflater2
                , activity
                , ContextThemeWrapper.class
                , "mInflater");



    }
    private static void injectLayoutInflater(LayoutInflater layoutInflater, Object src,Class clz, String name){
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

    public static void onModeChanged(int mode, Activity activity, @NonNull ViewGroup vp){

    }

    public static void handleViewClz(Class<View> clz){
        sStyleableTable.put(clz,generateHandler());
    }

    // TODO: 15/11/5 impl it later.
    private static Class<IStyleableHandler> generateHandler() {
        return null;
    }

    private static NightOwl sharedInstance(){
        checkNonNull(sInstance,"You must create NightOwl in Application onCreate.");
        return sInstance;
    }

    public static class Builder {

        public NightOwl create(){
            if ( sInstance != null ) throw new RuntimeException("Do not create NightOwl again.");
            sInstance = new NightOwl();
            return sInstance;
        }
    }
}
