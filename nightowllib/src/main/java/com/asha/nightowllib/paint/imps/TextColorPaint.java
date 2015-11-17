package com.asha.nightowllib.paint.imps;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.asha.nightowllib.paint.IOwlPaint;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 *
 */
public class TextColorPaint implements IOwlPaint {


    @Override
    public void draw(View view, Object value) {
        TextView tv = (TextView) view;
        ColorStateList color = (ColorStateList) value;
        tv.setTextColor(color);
    }

    @Override
    public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
        TextView tv = (TextView) view;
        ColorStateList color1 = tv.getTextColors();
        ColorStateList color2 = a.getColorStateList(attr);
        return new ColorStateList[]{ color1, color2 };
    }
}
