package com.asha.nightowllib.inflater;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class Factory4InjectedInflater {
    public static LayoutInflater newInstance(LayoutInflater original, Context newContext) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 11) {
            return new InjectedInflaterV11(original,newContext);
        } else {
            return new InjectedInflaterV7(original,newContext);
        }
    }
}
