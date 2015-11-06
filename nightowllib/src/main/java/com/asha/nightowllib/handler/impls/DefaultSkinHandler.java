package com.asha.nightowllib.handler.impls;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.asha.nightowllib.handler.ISkinHandler;

import static com.asha.nightowllib.NightOwlUtil.insertSkinContext;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class DefaultSkinHandler implements ISkinHandler {
    private static final String TAG = "DefaultSkinHandler";

    @Override
    public void collect(View view, Context context, AttributeSet attrs) {
        insertSkinContext(view,TAG);
        Log.d(TAG,String.format("collected %s %s %s",view,context,attrs));
    }

    @Override
    public void onModeChanged(int mode, View view) {

    }
}
