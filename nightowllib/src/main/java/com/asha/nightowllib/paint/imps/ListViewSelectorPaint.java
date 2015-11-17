package com.asha.nightowllib.paint.imps;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.asha.nightowllib.paint.IOwlPaint;

/**
 * Created by hzqiujiadi on 15/11/13.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class ListViewSelectorPaint implements IOwlPaint {
    @Override
    public void draw(@NonNull View view, @NonNull Object value) {
        ListView listView = (ListView) view;
        listView.setSelector((Drawable) value);
    }

    @Override
    public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
        ListView listView = (ListView) view;
        Drawable drawable1 = listView.getSelector();
        Drawable drawable2 = a.getDrawable(attr);
        return new Drawable[]{ drawable1, drawable2 };
    }
}
