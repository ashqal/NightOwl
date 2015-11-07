package com.asha.nightowllib;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.asha.nightowllib.handler.ISkinHandler;
import com.asha.nightowllib.handler.impls.DefaultSkinHandler;
import com.asha.nightowllib.inflater.Factory4InjectedInflater;

import java.lang.reflect.Field;
import java.util.HashMap;

import static com.asha.nightowllib.NightOwlUtil.checkHandler;
import static com.asha.nightowllib.NightOwlUtil.checkNonNull;
import static com.asha.nightowllib.NightOwlUtil.checkViewCollected;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class NightOwl {
    private static final String TAG = "NightOwl";
    private static NightOwl sInstance;

    HashMap<Class<? extends View>,ISkinHandler> mHandlers;
    private static HashMap<Class<? extends View>,Class<ISkinHandler>> sStyleableTable = new HashMap<>();
    private NightOwl(){
        mHandlers = new HashMap<>();
    }

    public static void inject(Activity activity){
        Window window = activity.getWindow();
        LayoutInflater layoutInflater = window.getLayoutInflater();

        LayoutInflater injectLayoutInflater1 = Factory4InjectedInflater.newInstance(layoutInflater, activity);
        injectLayoutInflater(injectLayoutInflater1
                ,activity.getWindow()
                ,activity.getWindow().getClass()
                ,"mLayoutInflater");

        LayoutInflater injectLayoutInflater2 = injectLayoutInflater1.cloneInContext(activity);
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

    public static void handleViewCreated(@NonNull View view, @NonNull AttributeSet attrs) {
        // check the view has been collected
        if ( checkViewCollected(view) ) return;
        NightOwl nightOwl = sharedInstance();

        // query the handler
        ISkinHandler handler = nightOwl.queryHandler(view.getClass());
        if ( !checkHandler(handler,view) ) return;

        // do collect
        handler.collect(view,view.getContext(),attrs);

    }



    private ISkinHandler queryHandler(Class clz) {
        return new DefaultSkinHandler();
    }

    // TODO: 15/11/5 impl it later.
    private static Class<ISkinHandler> generateHandler() {
        return null;
    }

    private static NightOwl sharedInstance(){
        checkNonNull(sInstance,"You must create NightOwl in Application onCreate.");
        return sInstance;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        public NightOwl create(){
            if ( sInstance != null ) throw new RuntimeException("Do not create NightOwl again.");
            sInstance = new NightOwl();
            return sInstance;
        }
    }
}
