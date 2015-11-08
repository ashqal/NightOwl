package com.asha.nightowllib.handler;

import com.asha.nightowllib.R;
import com.asha.nightowllib.handler.annotations.OwlAttr;
import com.asha.nightowllib.handler.annotations.OwlAttrScope;
import com.asha.nightowllib.handler.annotations.OwlStyleable;
import com.asha.nightowllib.paint.imps.BackgroundPaint;
import com.asha.nightowllib.paint.imps.TextColorPaint;

/**
 * Created by hzqiujiadi on 15/11/8.
 * hzqiujiadi ashqalcn@gmail.com
 */

public interface IOwlStyleable {

    @OwlAttrScope(1000) interface OwlView {
        @OwlStyleable int[] NightOwl_View = R.styleable.NightOwl_View;
        @OwlAttr(BackgroundPaint.class) int NightOwl_View_night_background = R.styleable.NightOwl_View_night_background;
    }




    @OwlAttrScope(2000) interface OwlTextView extends IOwlStyleable.OwlView {
        @OwlStyleable int[] NightOwl_TextView = R.styleable.NightOwl_TextView;
        @OwlAttr(TextColorPaint.class) int NightOwl_TextView_night_textColor = R.styleable.NightOwl_TextView_night_textColor;
        @OwlAttr(TextColorPaint.class) int NightOwl_TextView_night_textColorHint = R.styleable.NightOwl_TextView_night_textColorHint;
    }
}
