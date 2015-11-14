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
    private int mMode = -1;
    // may override the value, so we use SparseArray
    private SparseArray<Object[]> mBox;
    private ColorBox() {
        mBox = new SparseArray<>(4);
    }

    public void put(int attr, int scope, @NonNull Object... objects){
        mBox.put(attr + scope, objects);
    }

    public void refreshSkin(int mode, View view, boolean force){
        if ( force ) mMode = -1;
        refreshSkin(mode,view);
    }

    public Object[] get(int attr, int scope){
        return mBox.get( attr + scope );
    }

    public void refreshSkin(int mode, View view){
        if ( mMode != mode ){
            int size = mBox.size();
            for (int i = 0; i < size; i++) {
                int attrWithScope = mBox.keyAt(i);
                Object[] res = mBox.valueAt(i);
                IOwlPaint paint = queryPaint(attrWithScope);
                if ( paint != null ) paint.draw(view, res[mode]);
            }
        }
        mMode = mode;
    }

    public int getMode() {
        return mMode;
    }

    public static ColorBox newInstance() {
        return new ColorBox();
    }

}
