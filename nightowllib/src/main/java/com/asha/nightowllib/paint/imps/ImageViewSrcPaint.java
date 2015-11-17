package com.asha.nightowllib.paint.imps;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.asha.nightowllib.paint.IOwlPaint;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 *
 */
public class ImageViewSrcPaint implements IOwlPaint {

    @Override
    public void draw(View view, Object value) {
        ImageView imageView = (ImageView) view;
        Drawable drawable = (Drawable) value;
        imageView.setImageDrawable(drawable);
    }

    @Override
    public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
        ImageView imageView = (ImageView) view;
        Drawable bg1 = imageView.getDrawable();
        Drawable bg2 = a.getDrawable(attr);
        return new Drawable[]{ bg1, bg2 };
    }
}
