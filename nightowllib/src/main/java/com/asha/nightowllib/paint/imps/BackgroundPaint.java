package com.asha.nightowllib.paint.imps;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

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
        setBackgroundCompact(view,drawable);
    }

    @Override
    public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
        Drawable bg1 = view.getBackground();
        Drawable bg2 = a.getDrawable(attr);
        return new Drawable[]{bg1, bg2};
    }

    private void setBackgroundCompact(View view,Drawable drawable){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            setBackgroundPrev15(view, drawable);
        }
    }

    @SuppressWarnings("deprecation")
    private void setBackgroundPrev15(View view,Drawable drawable){
        view.setBackgroundDrawable(drawable);
    }
}
