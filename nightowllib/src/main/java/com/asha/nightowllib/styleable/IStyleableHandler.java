package com.asha.nightowllib.styleable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public interface IStyleableHandler {
    public static final int NIGHT_OWL_VIEW_TAG = (2 >>> 24);
    void collect(View view, Context context, AttributeSet attrs);
    void onModeChanged(int mode, View view);
}
