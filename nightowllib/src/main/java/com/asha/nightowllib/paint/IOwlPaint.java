package com.asha.nightowllib.paint;

import android.content.res.TypedArray;
import android.view.View;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 *
 * response for setup values and
 * draw draw draw draw
 */
public interface IOwlPaint {
    void draw(View view, Object value);
    Object[] setup(View view, TypedArray a, int attr);
}
