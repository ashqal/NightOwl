package com.asha.nightowllib.observer;

import android.app.Activity;

/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 *
 * do not save any context or view here
 * neither its impls
 */
public interface IOwlObserver {
    int getObserverId();
    void onSkinChange(int mode, Activity activity);
}
