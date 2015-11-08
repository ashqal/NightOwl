package com.asha.nightowllib.handler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public interface ISkinHandler {
    void collect(int mode, View view, Context context, AttributeSet attrs);
    void onSkinChanged(int skin, View view);
}
