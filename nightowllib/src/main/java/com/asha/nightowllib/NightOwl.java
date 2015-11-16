package com.asha.nightowllib;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.asha.nightowllib.handler.ISkinHandler;
import com.asha.nightowllib.handler.OwlHandlerManager;
import com.asha.nightowllib.inflater.Factory4InjectedInflater;
import com.asha.nightowllib.observer.IOwlObserver;
import com.asha.nightowllib.observer.OwlViewContext;
import com.asha.nightowllib.paint.ColorBox;
import com.asha.nightowllib.paint.OwlPaintManager;

import static com.asha.nightowllib.NightOwlUtil.checkNonNull;
import static com.asha.nightowllib.NightOwlUtil.checkViewCollected;
import static com.asha.nightowllib.NightOwlUtil.injectLayoutInflater;
import static com.asha.nightowllib.NightOwlUtil.insertEmptyTag;
import static com.asha.nightowllib.NightOwlUtil.insertViewContext;
import static com.asha.nightowllib.NightOwlUtil.obtainSkinBox;
import static com.asha.nightowllib.NightOwlUtil.obtainViewContext;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class NightOwl {
    private static final String TAG = "NightOwl";
    private static final String WINDOW_INFLATER = "mLayoutInflater";
    private static final String THEME_INFLATER = "mInflater";
    private static NightOwl sInstance;
    static {
        NightOwlTable.init();
    }

    private int mMode = 0;
    private IOwlObserver mOwlObserver;
    private NightOwl(){
    }

    public static void owlBeforeCreate(Activity activity){
        Window window = activity.getWindow();
        LayoutInflater layoutInflater = window.getLayoutInflater();

        // replace the inflater in window
        LayoutInflater injectLayoutInflater1 = Factory4InjectedInflater.newInstance(layoutInflater, activity);
        injectLayoutInflater(injectLayoutInflater1
                , activity.getWindow()
                , activity.getWindow().getClass()
                , WINDOW_INFLATER);

        // replace the inflater in current ContextThemeWrapper
        LayoutInflater injectLayoutInflater2 = injectLayoutInflater1.cloneInContext(activity);
        injectLayoutInflater(injectLayoutInflater2
                , activity
                , ContextThemeWrapper.class
                , THEME_INFLATER);

        // insert owlViewContext into root view.
        View v = activity.getWindow().getDecorView();
        OwlViewContext owlObservable = new OwlViewContext();
        insertViewContext(v, owlObservable);

    }


    public static void owlAfterCreate(Activity activity){
        View root = activity.getWindow().getDecorView();
        OwlViewContext viewContext = obtainViewContext(root);
        checkNonNull(viewContext, "OwlViewContext can not be null!");

        // setup some observer
        viewContext.setupWithCurrentActivity(activity);

        // init set
        viewContext.notifyObserver(sharedInstance().mMode, activity);
    }

    public static void owlResume( Activity activity ){
        NightOwl nightOwl = sharedInstance();
        int targetMode = nightOwl.mMode;

        owlDressUp(targetMode, activity);
    }

    public static void owlNewDress( @NonNull Activity activity ) {
        int current = owlCurrentMode() + 1;
        current %= 2;

        owlDressUp(current, activity);
    }

    public static void owlRecyclerFix(View view){
        int mode = owlCurrentMode();
        innerRefreshSkin(mode, view);
    }

    private static void owlDressUp( int mode, @NonNull Activity activity ){
        // View tree
        NightOwl owl = sharedInstance();
        View root = activity.getWindow().getDecorView();
        OwlViewContext viewContext = obtainViewContext(root);
        checkNonNull(viewContext, "OwlViewContext can not be null!");

        if ( viewContext.needSync(mode) ){
            // refresh skin
            innerRefreshSkin(mode, root);

            // OwlObserver
            viewContext.notifyObserver(mode, activity);
        }

        owl.mMode = mode;
        if ( owl.mOwlObserver != null ) owl.mOwlObserver.onSkinChange(mode,activity);
    }

    /**
     * Register a custom view which created by new instance directly.
     *
     * @param view instanceof IOwlObserver & View
     *             NightOwl will trigger view.onSkinChange immediately.
     */
    public static void owlRegisterCustom(@NonNull IOwlObserver view){
        if ( view instanceof View ) {
            View target = (View) view;
            insertEmptyTag(target);
            view.onSkinChange(owlCurrentMode(), null);
        } else {
            throw new IllegalArgumentException("owlRegisterCustom param must be a instance of View");
        }
    }

    public static void owlRegisterHandler(Class<? extends ISkinHandler> clz, Class paintTable){
        OwlHandlerManager.registerHandler(clz);
        OwlPaintManager.registerPaint(paintTable);
    }

    public static int owlCurrentMode(){
        return sharedInstance().mMode;
    }

    private static void innerRefreshSkin(int mode, View view ){
        // refresh current view
        if ( checkViewCollected(view) ){
            ColorBox box = obtainSkinBox(view);
            if ( box != null ) box.refreshSkin(mode, view);
            if ( view instanceof IOwlObserver ){
                ((IOwlObserver) view).onSkinChange(mode,null);
            }
        }
        // traversal view tree
        if ( view instanceof ViewGroup){
            ViewGroup vg = (ViewGroup) view;
            View sub;
            for (int i = 0; i < vg.getChildCount(); i++) {
                sub = vg.getChildAt(i);
                innerRefreshSkin(mode, sub);
            }
        }
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
        private IOwlObserver owlObserver;
        public Builder defaultMode(int mode){
            this.mode = mode;
            return this;
        }

        /**
         * Subscribed by a owlObserver to know the skin change.
         *
         * @param owlObserver do some persistent working here
         * @return Builder
         */
        public Builder subscribedBy(IOwlObserver owlObserver){
            this.owlObserver = owlObserver;
            return this;
        }
        public NightOwl create(){
            if ( sInstance != null ) throw new RuntimeException("Do not create NightOwl again.");
            sInstance = new NightOwl();
            sInstance.mMode = mode;
            sInstance.mOwlObserver = owlObserver;
            return sInstance;
        }
    }
}
