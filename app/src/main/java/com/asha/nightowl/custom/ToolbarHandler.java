package com.asha.nightowl.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.internal.view.menu.BaseMenuPresenter;
import android.support.v7.internal.view.menu.MenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asha.nightowllib.NightOwlUtil;
import com.asha.nightowllib.handler.annotations.OwlHandle;
import com.asha.nightowllib.handler.impls.AbsSkinHandler;
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
        private static Field sActionMenuViewField;
        private static Field sPresenterField;
        private static Field sContextField;
        static {
            try {
                sActionMenuViewField = Toolbar.class.getDeclaredField("mMenuView");
                sActionMenuViewField.setAccessible(true);

                sPresenterField = ActionMenuView.class.getDeclaredField("mPresenter");
                sPresenterField.setAccessible(true);

                sContextField = BaseMenuPresenter.class.getDeclaredField("mContext");
                sContextField.setAccessible(true);
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
                    toolbar.getContext().setTheme(themeId);
                } else {
                    MenuPresenter presenter = (MenuPresenter) sPresenterField.get(actionMenuView);
                    Context context = (Context) sContextField.get(presenter);
                    context.setTheme(themeId);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            toolbar.setPopupTheme((Integer) value);
        }

        @Override
        public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
            Toolbar toolbar = (Toolbar) view;
            int theme1 = toolbar.getPopupTheme();
            int theme2 = a.getResourceId(attr,0);
            return new Integer[]{theme1,theme2};
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
        public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
            Toolbar toolbar = (Toolbar) view;
            int color1 = NightOwlUtil.getFieldIntSafely(Toolbar.class, "mTitleTextColor", toolbar);
            int color2 = a.getColor(attr,color1);
            return new Integer[]{ color1,color2 };
        }
    }
}
