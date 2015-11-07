package com.asha.nightowllib.paint;

import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class ColorBox {
    // may override the value, so we use SparseArray
    private SparseArray<Object[]> mBox;
    protected ColorBox() {
        mBox = new SparseArray<>();
    }
    public void put(int attr, @NonNull Object... objects){
        mBox.put(attr, objects);
    }
    public void changeSkin(int skin, View view, SparseArray<IMagicPaint> paints){
        int size = mBox.size();
        for (int i = 0; i < size; i++) {
            int attr = mBox.keyAt(i);
            Object[] res = mBox.valueAt(i);
            IMagicPaint paint = paints.get(attr);
            paint.draw(view, res[skin]);
        }
    }
    public static ColorBox newInstance() {
        return new ColorBox();
    }

}
