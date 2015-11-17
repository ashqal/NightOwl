package com.asha.nightowl.custom;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;

import com.asha.nightowllib.handler.annotations.OwlHandle;
import com.asha.nightowllib.handler.impls.AbsSkinHandler;
import com.asha.nightowllib.paint.IOwlPaint;

/**
 * Created by hzqiujiadi on 15/11/11.
 * hzqiujiadi ashqalcn@gmail.com
 */
@OwlHandle(CollapsingToolbarLayout.class)
public class CollapsingToolbarLayoutHandler extends AbsSkinHandler implements OwlCustomTable.OwlCollapsingToolbarLayout {
    //NightOwl_CollapsingToolbarLayout_night_contentScrim

    public static class ContentScrimPaint implements IOwlPaint{

        @Override
        public void draw(@NonNull View view, @NonNull Object value) {
            CollapsingToolbarLayout layout = (CollapsingToolbarLayout) view;
            layout.setContentScrim((Drawable) value);
        }

        @Override
        public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
            CollapsingToolbarLayout layout = (CollapsingToolbarLayout) view;
            Drawable drawable1 = layout.getContentScrim();
            Drawable drawable2 = a.getDrawable(attr);
            return new Drawable[]{drawable1,drawable2};
        }
    }
}
