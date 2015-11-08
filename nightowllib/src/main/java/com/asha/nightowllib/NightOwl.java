package com.asha.nightowllib;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.asha.nightowllib.handler.HandlerManager;
import com.asha.nightowllib.handler.ISkinHandler;
import com.asha.nightowllib.inflater.Factory4InjectedInflater;
import com.asha.nightowllib.paint.ColorBox;

import java.util.concurrent.atomic.AtomicInteger;

import static com.asha.nightowllib.NightOwlUtil.checkHandler;
import static com.asha.nightowllib.NightOwlUtil.checkNonNull;
import static com.asha.nightowllib.NightOwlUtil.checkViewCollected;
import static com.asha.nightowllib.NightOwlUtil.injectLayoutInflater;
import static com.asha.nightowllib.NightOwlUtil.obtainSkinBox;
import static com.asha.nightowllib.handler.HandlerManager.queryHandler;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class NightOwl {
    private static final String TAG = "NightOwl";
    private static final String WINDOW_INFLATER = "mLayoutInflater";
    private static final String THEME_INFLATER = "mInflater";
    private AtomicInteger mMode = new AtomicInteger(0);
    private static NightOwl sInstance;
    static {
        NightOwlTable.init();
    }

    private NightOwl(){
    }

    public static void inject(Activity activity){
        Window window = activity.getWindow();
        LayoutInflater layoutInflater = window.getLayoutInflater();

        LayoutInflater injectLayoutInflater1 = Factory4InjectedInflater.newInstance(layoutInflater, activity);
        injectLayoutInflater(injectLayoutInflater1
                , activity.getWindow()
                , activity.getWindow().getClass()
                , WINDOW_INFLATER);

        LayoutInflater injectLayoutInflater2 = injectLayoutInflater1.cloneInContext(activity);
        injectLayoutInflater(injectLayoutInflater2
                , activity
                , ContextThemeWrapper.class
                , THEME_INFLATER);
    }

    public static void refreshSkin(int mode, @NonNull Activity activity){
        NightOwl owl = sharedInstance();
        if ( owl.mMode.get() != mode ){
            View root = activity.getWindow().getDecorView();
            innerRefreshSkin( mode, root );
            owl.mMode.set(mode);
        }
    }

    private static void innerRefreshSkin(int mode, View view){
        if ( checkViewCollected(view) ){
            ColorBox box = obtainSkinBox(view);
            if ( box != null ) box.refreshSkin(mode, view);
        }

        if ( view instanceof ViewGroup){
            ViewGroup vg = (ViewGroup) view;
            View sub;
            for (int i = 0; i < vg.getChildCount(); i++) {
                sub = vg.getChildAt(i);
                innerRefreshSkin(mode, sub);
            }
        }
    }

    public static void registerViewClz(Class<View> clz){
        HandlerManager.registerView(clz);
    }

    public static void handleViewCreated(@NonNull View view, @NonNull AttributeSet attrs) {
        // check the view has been collected
        if ( checkViewCollected(view) ) return;

        // query the handler
        ISkinHandler handler = queryHandler(view.getClass());
        if ( !checkHandler(handler,view) ) return;

        NightOwl owl = sharedInstance();
        // do collect
        handler.collect(owl.mMode.get(), view, view.getContext(), attrs);
    }

    private static NightOwl sharedInstance(){
        checkNonNull(sInstance,"You must create NightOwl in Application onCreate.");
        return sInstance;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private int mode;
        public Builder defualt(int mode){
            this.mode = mode;
            return this;
        }
        public NightOwl create(){
            if ( sInstance != null ) throw new RuntimeException("Do not create NightOwl again.");
            sInstance = new NightOwl();
            sInstance.mMode.set(mode);
            return sInstance;
        }
    }
}
