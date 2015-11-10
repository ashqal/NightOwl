package com.asha.nightowl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.asha.nightowllib.NightOwl;

/**
 * Created by hzqiujiadi on 15/11/10.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class RecyclerViewFix extends RecyclerView {
    public RecyclerViewFix(Context context) {
        super(context);
    }

    public RecyclerViewFix(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewFix(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        NightOwl.owlRecyclerVHFix(child);
        super.addView(child, index, params);
    }
}
