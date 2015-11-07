package com.asha.nightowllib.paint;

import android.support.annotation.NonNull;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class ColorBox {
    private List<Pair<Integer,Object[]>> mBox;
    protected ColorBox() {
        mBox = new LinkedList<>();
    }
    public void push(int attr, @NonNull Object... objects){
        mBox.add(new Pair<Integer, Object[]>(attr,objects));
    }
    public void changeSkin(int skin, View view, SparseArray<IMagicPaint> paints){
        Iterator<Pair<Integer,Object[]>> iter = mBox.iterator();
        while ( iter.hasNext() ){
            Pair<Integer,Object[]> pair = iter.next();
            int attr = pair.first;
            Object[] res = pair.second;
            IMagicPaint paint = paints.get(attr);
            paint.draw(view, res[skin]);
        }
    }
    public static ColorBox newInstance() {
        return new ColorBox();
    }

}
