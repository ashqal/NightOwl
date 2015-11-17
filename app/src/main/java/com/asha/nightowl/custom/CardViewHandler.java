package com.asha.nightowl.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.asha.nightowl.R;
import com.asha.nightowllib.handler.annotations.OwlHandle;
import com.asha.nightowllib.handler.impls.AbsSkinHandler;
import com.asha.nightowllib.paint.ColorBox;
import com.asha.nightowllib.paint.IOwlPaint;

/**
 * Created by hzqiujiadi on 15/11/12.
 * hzqiujiadi ashqalcn@gmail.com
 */
@OwlHandle(CardView.class)
public class CardViewHandler extends AbsSkinHandler implements OwlCustomTable.OwlCardView {

    @Override
    protected void onAfterCollect(View view, Context context, AttributeSet attrs, ColorBox box) {
        Object[] objects = box.get(R.styleable.NightOwl_CardView_night_cardBackgroundColor
                , OwlCustomTable.CardViewScope);
        if ( objects != null ){
            // obtain original color
            TypedArray a = context.obtainStyledAttributes(attrs, android.support.v7.cardview.R.styleable.CardView, 0,
                    android.support.v7.cardview.R.style.CardView_Light);
            if ( a != null ){
            int backgroundColor = a.getColor(android.support.v7.cardview.R.styleable.CardView_cardBackgroundColor, 0);
                objects[0] = backgroundColor;
                a.recycle();
            }
        }
    }

    public static class BackgroundPaint implements IOwlPaint{

        @Override
        public void draw(@NonNull View view, @NonNull Object value) {
            CardView cardView = (CardView) view;
            cardView.setCardBackgroundColor((Integer) value);
        }

        @Override
        public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
            int color1 = 0;
            int color2 = a.getColor(attr,0);

            return new Integer[]{ color1,color2};
        }
    }
}
