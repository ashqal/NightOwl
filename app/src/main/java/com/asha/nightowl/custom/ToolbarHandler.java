package com.asha.nightowl.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asha.nightowl.R;
import com.asha.nightowllib.NightOwlUtil;
import com.asha.nightowllib.handler.annotations.OwlHandle;
import com.asha.nightowllib.handler.impls.AbsSkinHandler;
import com.asha.nightowllib.paint.ColorBox;
import com.asha.nightowllib.paint.IOwlPaint;

import java.lang.reflect.Field;

/**
 * Created by hzqiujiadi on 15/11/11.
 * hzqiujiadi ashqalcn@gmail.com
 */
@OwlHandle(Toolbar.class)
public class ToolbarHandler extends AbsSkinHandler implements OwlCustomTable.OwlToolbar {
    public ToolbarHandler() {
    }

    public static class PopupThemePaint implements IOwlPaint{
        //private ActionMenuView mMenuView;
        private static Field sActionMenuViewField;
        private static Field sPopupContextField;
        static {
            try {
                sActionMenuViewField = Toolbar.class.getDeclaredField("mMenuView");
                sActionMenuViewField.setAccessible(true);

                sPopupContextField = ActionMenuView.class.getDeclaredField("mPopupContext");
                sPopupContextField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void draw(@NonNull View view, @NonNull Object value) {
            Toolbar toolbar = (Toolbar) view;
            int themeId = (int) value;
            try {
                ActionMenuView actionMenuView = (ActionMenuView) sActionMenuViewField.get(toolbar);
                if ( actionMenuView == null ){
                    toolbar.getContext().getTheme().applyStyle(themeId,true);
                } else {
                    Context context = (Context) sPopupContextField.get(actionMenuView);
                    context.getTheme().applyStyle(themeId,true);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            toolbar.setPopupTheme((Integer) value);
        }

        @Override
        public void setup(@NonNull View view, @NonNull TypedArray a, int attr, int scope, @NonNull ColorBox into) {
            int theme1 = R.style.AdditionThemeDay;//toolbar.getPopupTheme();
            int theme2 = R.style.AdditionThemeNight;//a.getResourceId(attr,0);

            into.put(attr,scope,theme1,theme2);
        }
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
