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
public class NavBarObserver implements IOwlObserverWithId {
    int mNavigationBarColor;
    int mNavigationBarColorNight;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NavBarObserver(Activity activity, TypedArray a, int attr) {
        Window window = activity.getWindow();
        mNavigationBarColor = window.getNavigationBarColor();
        mNavigationBarColorNight = a.getColor(attr,mNavigationBarColor);
    }

    @Override
    public int getObserverId() {
        return NavBarObserver.this.hashCode();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSkinChange(int mode, Activity activity) {
        activity.getWindow().setNavigationBarColor( mode == 0 ? mNavigationBarColor : mNavigationBarColorNight);
    }
}
