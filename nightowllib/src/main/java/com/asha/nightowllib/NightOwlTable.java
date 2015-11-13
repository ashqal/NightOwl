package com.asha.nightowllib;

import com.asha.nightowllib.handler.annotations.OwlAttr;
import com.asha.nightowllib.handler.annotations.OwlAttrScope;
import com.asha.nightowllib.handler.annotations.OwlStyleable;
import com.asha.nightowllib.handler.impls.ButtonHandler;
import com.asha.nightowllib.handler.impls.ImageViewHandler;
import com.asha.nightowllib.handler.impls.ListViewHandler;
import com.asha.nightowllib.handler.impls.TextViewHandler;
import com.asha.nightowllib.handler.impls.ViewHandler;
import com.asha.nightowllib.paint.imps.AlphaPaint;
import com.asha.nightowllib.paint.imps.BackgroundPaint;
import com.asha.nightowllib.paint.imps.ImageViewSrcPaint;
import com.asha.nightowllib.paint.imps.ListViewDividerPaint;
import com.asha.nightowllib.paint.imps.ListViewSelectorPaint;
import com.asha.nightowllib.paint.imps.TextColorPaint;

import static com.asha.nightowllib.handler.OwlHandlerManager.registerHandler;

/**
 * Created by hzqiujiadi on 15/11/8.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class NightOwlTable {
    protected static void init(){
        registerHandler(ListViewHandler.class);
        registerHandler(ImageViewHandler.class);
        registerHandler(TextViewHandler.class);
        registerHandler(ButtonHandler.class);
        registerHandler(ViewHandler.class);
    }

    @OwlAttrScope(2000) public interface OwlView {
        @OwlStyleable int[] NightOwl_View = R.styleable.NightOwl_View;
        @OwlAttr(BackgroundPaint.class) int NightOwl_View_night_background = R.styleable.NightOwl_View_night_background;
        @OwlAttr(AlphaPaint.class) int NightOwl_View_night_alpha = R.styleable.NightOwl_View_night_alpha;
    }

    @OwlAttrScope(2100) public interface OwlTextView extends OwlView {
        @OwlStyleable int[] NightOwl_TextView = R.styleable.NightOwl_TextView;
        @OwlAttr(TextColorPaint.class) int NightOwl_TextView_night_textColor = R.styleable.NightOwl_TextView_night_textColor;
        @OwlAttr(TextColorPaint.class) int NightOwl_TextView_night_textColorHint = R.styleable.NightOwl_TextView_night_textColorHint;
    }

    @OwlAttrScope(2200) public interface OwlButton extends OwlTextView {
    }

    @OwlAttrScope(2300) public interface OwlImageView extends OwlView {
        @OwlStyleable int[] NightOwl_ImageView =  R.styleable.NightOwl_ImageView;
        @OwlAttr(ImageViewSrcPaint.class) int NightOwl_ImageView_night_src = R.styleable.NightOwl_ImageView_night_src;
    }

    @OwlAttrScope(2400) public interface OwlListView extends OwlView {
        @OwlStyleable int[] NightOwl_ListView =  R.styleable.NightOwl_ListView;
        @OwlAttr(ListViewDividerPaint.class) int NightOwl_ListView_night_divider = R.styleable.NightOwl_ListView_night_divider;
        @OwlAttr(ListViewSelectorPaint.class) int NightOwl_ListView_night_listSelector = R.styleable.NightOwl_ListView_night_listSelector;
    }
}
