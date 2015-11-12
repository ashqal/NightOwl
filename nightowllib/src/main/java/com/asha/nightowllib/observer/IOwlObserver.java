package com.asha.nightowllib.observer;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 *
 * do not save any context or view here
 * neither its impls
 */
public interface IOwlObserver {

    /**
     *
     * @param mode current style mode
     * @param activity notice that: it may be null
     *
     * @see com.asha.nightowllib.inflater.InjectedInflaterBase#handleOnCreateView(View, String, AttributeSet)
     */
    void onSkinChange(int mode, Activity activity);
}
