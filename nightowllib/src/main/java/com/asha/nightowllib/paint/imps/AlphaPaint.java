package com.asha.nightowllib.paint.imps;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.asha.nightowllib.paint.IOwlPaint;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 *
 */
public class AlphaPaint implements IOwlPaint {

    @Override
    public void draw(View view, Object value) {
        Float alpha = (Float) value;
        ViewCompat.setAlpha(view,alpha);
    }

    @Override
    public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {

        Float alpha1 = ViewCompat.getAlpha(view);
        Float alpha2 = a.getFloat(attr, alpha1);
        return new Float[]{ alpha1, alpha2 };
    }
}
