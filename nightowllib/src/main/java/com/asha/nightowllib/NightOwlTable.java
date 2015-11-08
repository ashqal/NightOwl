package com.asha.nightowllib;

import com.asha.nightowllib.handler.annotations.OwlAttr;
import com.asha.nightowllib.handler.annotations.OwlAttrScope;
import com.asha.nightowllib.handler.annotations.OwlStyleable;
import com.asha.nightowllib.handler.impls.ButtonHandler;
import com.asha.nightowllib.handler.impls.ViewHandler;
import com.asha.nightowllib.handler.impls.TextViewHandler;
import com.asha.nightowllib.paint.imps.BackgroundPaint;
import com.asha.nightowllib.paint.imps.TextColorPaint;

import static com.asha.nightowllib.handler.HandlerManager.registerHandler;

/**
 * Created by hzqiujiadi on 15/11/8.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class NightOwlTable {
    protected static void init(){
        registerHandler(TextViewHandler.class);
        registerHandler(ButtonHandler.class);
        registerHandler(ViewHandler.class);
    }
    @OwlAttrScope(1000) public interface OwlView {
        @OwlStyleable int[] NightOwl_View = R.styleable.NightOwl_View;
        @OwlAttr(BackgroundPaint.class) int NightOwl_View_night_background = R.styleable.NightOwl_View_night_background;
    }

    @OwlAttrScope(2000) public interface OwlTextView extends NightOwlTable.OwlView {
        @OwlStyleable int[] NightOwl_TextView = R.styleable.NightOwl_TextView;
        @OwlAttr(TextColorPaint.class) int NightOwl_TextView_night_textColor = R.styleable.NightOwl_TextView_night_textColor;
        @OwlAttr(TextColorPaint.class) int NightOwl_TextView_night_textColorHint = R.styleable.NightOwl_TextView_night_textColorHint;
    }

    @OwlAttrScope(3000) public interface OwlButton extends OwlTextView {
    }
}
