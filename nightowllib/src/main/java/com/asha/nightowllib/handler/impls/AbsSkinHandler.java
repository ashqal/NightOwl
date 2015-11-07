package com.asha.nightowllib.handler.impls;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.asha.nightowllib.R;
import com.asha.nightowllib.handler.ISkinHandler;
import com.asha.nightowllib.paint.ColorBox;
import com.asha.nightowllib.paint.IMagicPaint;
import com.asha.nightowllib.paint.TextColorPaint;

import static com.asha.nightowllib.NightOwlUtil.insertSkinBox;
import static com.asha.nightowllib.NightOwlUtil.obtainSkinBox;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public abstract class AbsSkinHandler implements ISkinHandler {
    private static final String TAG = "AbsSkinHandler";
    private static final SparseArray<IMagicPaint> sPaints = new SparseArray<>();
    static {
        sPaints.append(R.styleable.NightOwl_TextView_night_textColor, new TextColorPaint());
    }
    @Override
    public void collect(View view, Context context, AttributeSet attrs) {
        Log.d(TAG, String.format("collected %s %s %s", view, context, attrs));
        ColorBox box = ColorBox.newInstance();

        // obtain style.
        final Resources.Theme theme = context.getTheme();
        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.NightOwl_TextView, 0, 0);
        if (a != null) {
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                IMagicPaint paint = sPaints.get(attr);
                if ( paint == null ){ Log.e(TAG,"Can't find paint of " + attr); continue; }
                paint.setup(view,a,attr,box);
            }
        }
        if ( a != null ) a.recycle();

        onCollectInto(box);
        insertSkinBox(view, box);
    }

    protected void onCollectInto(ColorBox box){}

    @Override
    final public void onSkinChanged(int skin, View view) {
        ColorBox box = obtainSkinBox(view);
        if ( box != null ) box.changeSkin(skin,view,sPaints);
    }
}
