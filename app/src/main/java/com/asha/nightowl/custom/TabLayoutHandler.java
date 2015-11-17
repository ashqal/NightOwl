package com.asha.nightowl.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

import com.asha.nightowl.R;
import com.asha.nightowllib.handler.annotations.OwlHandle;
import com.asha.nightowllib.handler.impls.AbsSkinHandler;
import com.asha.nightowllib.paint.ColorBox;
import com.asha.nightowllib.paint.IOwlPaint;

/**
 * Created by hzqiujiadi on 15/11/10.
 * hzqiujiadi ashqalcn@gmail.com
 */
@OwlHandle(TabLayout.class)
public class TabLayoutHandler extends AbsSkinHandler implements OwlCustomTable.OwlTabLayout {

    @Override
    protected void onAfterCollect(View view, Context context, AttributeSet attrs, ColorBox box) {

        Object[] objects = box.get(R.styleable.NightOwl_TabLayout_night_tabIndicatorColor
                                , OwlCustomTable.TabLayoutScope);
        if ( objects != null ){
            // obtain color
            TypedArray a = context.obtainStyledAttributes(attrs, android.support.design.R.styleable.TabLayout,
                    0, android.support.design.R.style.Widget_Design_TabLayout);
            if ( a != null ){
                int color = a.getColor(android.support.design.R.styleable.TabLayout_tabIndicatorColor, 0);
                objects[0] = color;
                a.recycle();
            }
        }
    }

    public static class TextColorPaint implements IOwlPaint {
        @Override
        public void draw(@NonNull View view, @NonNull Object value) {
            TabLayout tabLayout = (TabLayout) view;
            ColorStateList csl = (ColorStateList) value;
            tabLayout.setTabTextColors(csl);
        }

        @Override
        public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
            TabLayout tabLayout = (TabLayout) view;
            ColorStateList csl1 = tabLayout.getTabTextColors();
            ColorStateList csl2 = a.getColorStateList(attr);
            return new ColorStateList[]{ csl1, csl2 };
        }
    }

    public static class IndicatorColorPaint implements IOwlPaint {

        @Override
        public void draw(@NonNull View view, @NonNull Object value) {
            TabLayout tabLayout = (TabLayout) view;
            int color = (int) value;
            tabLayout.setSelectedTabIndicatorColor(color);
        }

        @Override
        public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
            // there is no method getSelectedTabIndicatorColor
            // we can insert later
            int color = 0;
            int color2 = a.getColor(attr,0);
            return new Integer[]{color,color2};
        }

    }

}
