package com.asha.nightowllib.paint.imps;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import com.asha.nightowllib.paint.ColorBox;
import com.asha.nightowllib.paint.IOwlPaint;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 *
 */
public class BackgroundPaint implements IOwlPaint {

    @Override
    public void draw(View view, Object value) {
        Drawable drawable = (Drawable) value;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    @Override
    public void setup(@NonNull View view, @NonNull TypedArray a, int attr, int scope, @NonNull ColorBox into) {
        Drawable bg1 = view.getBackground();
        Drawable bg2 = a.getDrawable(attr);
        into.put(attr, scope, bg1, bg2);
    }



}
