package com.asha.nightowllib.observer;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.SparseArray;

import com.asha.nightowllib.R;
import com.asha.nightowllib.observer.impls.NavBarObserver;
import com.asha.nightowllib.observer.impls.StatusBarObserver;

/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class OwlViewContext {
    private int mLastMode = -1;
    private SparseArray<IOwlObserverWithId> observers;
    public OwlViewContext() {
        observers = new SparseArray<>();
    }

    public void registerObserver( IOwlObserverWithId owlObserver ){
        observers.put(owlObserver.getObserverId(),owlObserver);
    }

    public void unregisterObserver( IOwlObserverWithId owlObserver ){
        observers.delete(owlObserver.getObserverId());
    }

    public void notifyObserver(int mode, Activity activity){
        if ( mode == mLastMode ) return;
        mLastMode = mode;
        int size = observers.size();
        for (int i = 0; i < size; i++) {
            IOwlObserverWithId owlObserver = observers.valueAt(i);
            owlObserver.onSkinChange(mode, activity);
        }
    }

    public void setupWithCurrentActivity(Activity activity){
        Resources.Theme theme = activity.getTheme();
        TypedArray a = theme.obtainStyledAttributes(R.styleable.NightOwl_Theme);
        if (a != null) {
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.NightOwl_Theme_night_navigationBarColor) {
                    registerObserver(new NavBarObserver(activity, a, attr));
                } else if (attr == R.styleable.NightOwl_Theme_night_statusBarColor) {
                    registerObserver(new StatusBarObserver(activity, a, attr));
                }
            }
            a.recycle();
        }
    }

    public boolean needSync( int target ){
        return mLastMode != target;
    }
}
