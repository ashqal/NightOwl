package com.asha.nightowllib.handler.impls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class TextViewHandler extends AbsSkinHandler {
    @Override
    public void collect(View view, Context context, AttributeSet attrs) {
        super.collect(view, context, attrs);
        onSkinChanged(1,view);
    }
}
