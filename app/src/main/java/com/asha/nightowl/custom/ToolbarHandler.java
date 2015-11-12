package com.asha.nightowl.custom;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asha.nightowllib.NightOwlUtil;
import com.asha.nightowllib.handler.annotations.OwlHandle;
import com.asha.nightowllib.handler.impls.AbsSkinHandler;
import com.asha.nightowllib.paint.ColorBox;
import com.asha.nightowllib.paint.IOwlPaint;

/**
 * Created by hzqiujiadi on 15/11/11.
 * hzqiujiadi ashqalcn@gmail.com
 */
@OwlHandle(Toolbar.class)
public class ToolbarHandler extends AbsSkinHandler implements OwlCustomTable.OwlToolbar {
    public ToolbarHandler() {
    }

    public static class TitleTextColorPaint implements IOwlPaint{

        @Override
        public void draw(@NonNull View view, @NonNull Object value) {
            Toolbar toolbar = (Toolbar) view;
            int color = (int) value;
            toolbar.setTitleTextColor(color);
        }

        @Override
        public void setup(@NonNull View view, @NonNull TypedArray a, int attr, int scope, @NonNull ColorBox into) {
            Toolbar toolbar = (Toolbar) view;
            int color1 = NightOwlUtil.getFieldIntSafely(Toolbar.class, "mTitleTextColor", toolbar);
            if ( color1 == 0 ) color1 = 0xFFFFFFFF;
            int color2 = a.getColor(attr,color1);

            into.put(attr,scope,color1,color2);
        }
    }
}
