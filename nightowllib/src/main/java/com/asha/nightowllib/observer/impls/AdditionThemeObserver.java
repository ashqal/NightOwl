package com.asha.nightowllib.observer.impls;

import android.app.Activity;

import com.asha.nightowllib.observer.IOwlObserverWithId;

/**
 * Created by hzqiujiadi on 15/11/14.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class AdditionThemeObserver implements IOwlObserverWithId {

    private static final String TAG = "AdditionThemeObserver";
    private int mThemeDay;
    private int mThemeNight;
    public AdditionThemeObserver(int themeDay, int themeNight) {
        mThemeDay = themeDay;
        mThemeNight = themeNight;
    }

    @Override
    public int getObserverId() {
        return AdditionThemeObserver.this.hashCode();
    }

    @Override
    public void onSkinChange(int mode, Activity activity) {
        int theme = mode == 0 ? mThemeDay : mThemeNight;
        activity.getTheme().applyStyle(theme, true);

        //Log.e(TAG, String.format("%s %d", activity.getTheme(), theme));
    }
}
