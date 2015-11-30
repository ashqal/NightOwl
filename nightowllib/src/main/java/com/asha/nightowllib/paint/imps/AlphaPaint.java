package com.asha.nightowllib.paint.imps;

import android.content.res.TypedArray;
import android.os.Build;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setAlpha(alpha);
        }
    }

    @Override
    public Object[] setup(View view, TypedArray a, int attr) {


        Float alpha1 = 1.0f;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            alpha1 = view.getAlpha();
        }
        Float alpha2 = a.getFloat(attr, alpha1);
        return new Float[]{ alpha1, alpha2 };
    }
}
