package com.asha.nightowllib.handler;

import android.content.res.Resources;
import android.content.res.TypedArray;

/**
 * Created by hzqiujiadi on 15/11/8.
 * hzqiujiadi ashqalcn@gmail.com
 */
public abstract class AbsStyleableAttr {
    private TypedArray a;
    protected int[] attr_styleable;
    public int obtainResId(TypedArray a,int attr){
        return a.getResourceId(attr,-1);
    }
    public TypedArray obtainAttrStyle(int resId, Resources.Theme theme){
        a = theme.obtainStyledAttributes(
                resId, attr_styleable);
        return a;
    }
}
