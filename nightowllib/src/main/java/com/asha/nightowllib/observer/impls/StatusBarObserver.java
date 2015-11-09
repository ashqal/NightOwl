package com.asha.nightowllib.observer.impls;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Build;
import android.view.Window;

import com.asha.nightowllib.observer.IOwlObserverWithId;

/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class StatusBarObserver implements IOwlObserverWithId {
    int mStatusBarColor;
    int mStatusBarColorNight;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StatusBarObserver(Activity activity, TypedArray a, int attr) {
        Window window = activity.getWindow();
        mStatusBarColor = window.getStatusBarColor();
        mStatusBarColorNight = a.getColor(attr,mStatusBarColorNight);
    }

    @Override
    public int getObserverId() {
        return StatusBarObserver.this.hashCode();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSkinChange(int mode, Activity activity) {
        activity.getWindow().setStatusBarColor( mode == 0 ? mStatusBarColor : mStatusBarColorNight);
    }
}
