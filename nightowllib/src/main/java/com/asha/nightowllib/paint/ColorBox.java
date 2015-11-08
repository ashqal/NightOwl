package com.asha.nightowllib.paint;

import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;

import static com.asha.nightowllib.paint.OwlPaintManager.queryPaint;

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

    public void put(int attr, int scope, @NonNull Object... objects){
        mBox.put(attr + scope, objects);
    }
    public void changeSkin(int skin, View view){
        int size = mBox.size();
        for (int i = 0; i < size; i++) {
            int attrWithScope = mBox.keyAt(i);
            Object[] res = mBox.valueAt(i);
            IOwlPaint paint = queryPaint(attrWithScope);
            if ( paint != null ) paint.draw(view, res[skin]);
        }
    }
    public static ColorBox newInstance() {
        return new ColorBox();
    }

}
