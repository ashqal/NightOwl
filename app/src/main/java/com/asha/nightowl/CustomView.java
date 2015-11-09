package com.asha.nightowl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.asha.nightowllib.observer.IOwlObserver;

/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class CustomView extends View implements IOwlObserver {

    private Paint mPaint;
    private int mColor;
    private int mColorNight;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mColor = ContextCompat.getColor(getContext(),R.color.background_color_pressed);
        mColorNight = ContextCompat.getColor(getContext(),R.color.background_color_n_pressed);

        mPaint = new Paint();
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getMeasuredWidth()>>1
                , getMeasuredHeight()>>1
                , getMeasuredHeight()>>1
                , mPaint);
    }

    @Override
    public void onSkinChange(int mode, Activity activity) {
        int color = mode == 0 ? mColor : mColorNight;
        mPaint.setColor(color);
        this.invalidate();
    }
}
