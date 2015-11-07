package com.asha.nightowllib.handler.impls;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;

import com.asha.nightowllib.R;
import com.asha.nightowllib.handler.AbsStyleableAttr;
import com.asha.nightowllib.handler.ISkinHandler;
import com.asha.nightowllib.handler.attrs.TextAppearanceAttr;
import com.asha.nightowllib.paint.ColorBox;
import com.asha.nightowllib.paint.IMagicPaint;
import com.asha.nightowllib.paint.TextColorPaint;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.asha.nightowllib.NightOwlUtil.insertSkinBox;
import static com.asha.nightowllib.NightOwlUtil.obtainSkinBox;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public abstract class AbsSkinHandler implements ISkinHandler {
    private static final String TAG = "AbsSkinHandler";
    private static final SparseArray<IMagicPaint> sPaints = new SparseArray<>();
    private static final SparseArray<AbsStyleableAttr> sStyleableAttrs = new SparseArray<>();
    static {
        sPaints.append(R.styleable.NightOwl_TextView_night_textColor, new TextColorPaint());

        sStyleableAttrs.append(R.styleable.NightOwl_TextView_textAppearance, new TextAppearanceAttr());
    }
    @Override
    public void collect(View view, Context context, AttributeSet attrs) {
        Log.d(TAG, String.format("collected %s %s %s", view, context, attrs));
        ColorBox box = ColorBox.newInstance();
        onBeforeCollect(box);

        final Resources.Theme theme = context.getTheme();

        // obtain style.
        obtainStyle(view,attrs,box,theme,R.styleable.NightOwl_TextView);


        onAfterCollect(box);
        insertSkinBox(view, box);
    }

    final protected void obtainStyle(View view
            , AttributeSet attrs
            , ColorBox box
            , Resources.Theme theme
            , @StyleableRes int[] styleableResId){
        TypedArray a = theme.obtainStyledAttributes(attrs, styleableResId, 0, 0);
        if ( a != null ){
            // TypedArray will be recycled in obtainStyle
            obtainStyle(view, attrs, box, theme, a);
        }
    }
    final protected void obtainStyle(View view
            , AttributeSet attrs
            , ColorBox box
            , Resources.Theme theme
            , @NonNull TypedArray a){
        List<Pair<Integer,Integer>> styleableAttrList = null;

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            // look for styleable attr
            AbsStyleableAttr styleableAttr = sStyleableAttrs.get(attr);
            if ( styleableAttr != null  ){
                int resId = styleableAttr.obtainResId(a,attr);
                if ( resId != -1 ){
                    if ( styleableAttrList == null ) styleableAttrList = new LinkedList<>();
                    styleableAttrList.add(Pair.create(attr, resId));
                }
            }

            // look for attr
            IMagicPaint paint = sPaints.get(attr);
            if ( paint == null) {
                Log.e(TAG, "Can't find paint of " + attr); continue; }
            paint.setup(view,a,attr,box);
        }
        a.recycle();

        if ( styleableAttrList != null ){
            Iterator<Pair<Integer,Integer>> iterator = styleableAttrList.iterator();
            while ( iterator.hasNext() ){
                Pair<Integer,Integer> pair = iterator.next();
                AbsStyleableAttr styleableAttr = sStyleableAttrs.get(pair.first);
                TypedArray array = styleableAttr.obtainAttrStyle(pair.second, theme);
                if ( array != null ){
                    // TypedArray will be recycled in obtainStyle
                    obtainStyle(view,attrs,box,theme,array);
                }
            }
        }
    }



    protected void onBeforeCollect(ColorBox box){}

    protected void onAfterCollect(ColorBox box){}

    @Override
    final public void onSkinChanged(int skin, View view) {
        ColorBox box = obtainSkinBox(view);
        if ( box != null ) box.changeSkin(skin,view,sPaints);
    }
}
