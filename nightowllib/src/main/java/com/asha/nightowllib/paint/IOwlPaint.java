package com.asha.nightowllib.paint;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 *
 * response for setup values and
 * draw draw draw draw
 */
public interface IOwlPaint {
    void draw(@NonNull View view, @NonNull Object value);
    Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr);
}
