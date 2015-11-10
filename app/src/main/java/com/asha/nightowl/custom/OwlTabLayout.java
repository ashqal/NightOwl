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
@OwlAttrScope(TabLayoutHandler.TabLayoutScope) public interface OwlTabLayout extends NightOwlTable.OwlView {
    @OwlStyleable int[] NightOwl_TabLayout = R.styleable.NightOwl_TabLayout;
    @OwlAttr(TabLayoutHandler.TextColorPaint.class) int NightOwl_TabLayout_night_textColorSelector = R.styleable.NightOwl_TabLayout_night_textColorSelector;
    @OwlAttr(TabLayoutHandler.IndicatorColorPaint.class) int NightOwl_TabLayout_night_tabIndicatorColor = R.styleable.NightOwl_TabLayout_night_tabIndicatorColor;
}
