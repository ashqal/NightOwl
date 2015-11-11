package com.asha.nightowl.custom;

import com.asha.nightowl.R;
import com.asha.nightowllib.NightOwlTable;
import com.asha.nightowllib.handler.annotations.OwlAttr;
import com.asha.nightowllib.handler.annotations.OwlAttrScope;
import com.asha.nightowllib.handler.annotations.OwlStyleable;

/**
 * Created by hzqiujiadi on 15/11/11.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class OwlCustomTable {

    public static final int TabLayoutScope = 10000;
    @OwlAttrScope(TabLayoutScope) public interface OwlTabLayout extends NightOwlTable.OwlView {
        @OwlStyleable
        int[] NightOwl_TabLayout = R.styleable.NightOwl_TabLayout;
        @OwlAttr(TabLayoutHandler.TextColorPaint.class) int NightOwl_TabLayout_night_textColorSelector = R.styleable.NightOwl_TabLayout_night_textColorSelector;
        @OwlAttr(TabLayoutHandler.IndicatorColorPaint.class) int NightOwl_TabLayout_night_tabIndicatorColor = R.styleable.NightOwl_TabLayout_night_tabIndicatorColor;
    }

    @OwlAttrScope(11000) public interface OwlToolbar extends NightOwlTable.OwlView {
        @OwlStyleable
        int[] NightOwl_Toolbar = R.styleable.NightOwl_Toolbar;
        @OwlAttr(ToolbarHandler.TitleTextColorPaint.class) int NightOwl_Toolbar_night_titleTextColor = R.styleable.NightOwl_Toolbar_night_titleTextColor;
    }

    @OwlAttrScope(12000) public interface OwlCollapsingToolbarLayout {
        @OwlStyleable
        int[] NightOwl_CollapsingToolbarLayout = R.styleable.NightOwl_CollapsingToolbarLayout;
        @OwlAttr(CollapsingToolbarLayoutHandler.ContentScrimPaint.class) int NightOwl_CollapsingToolbarLayout_night_contentScrim = R.styleable.NightOwl_CollapsingToolbarLayout_night_contentScrim;
    }

}
