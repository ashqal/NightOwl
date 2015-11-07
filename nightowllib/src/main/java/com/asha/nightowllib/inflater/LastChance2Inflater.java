package com.asha.nightowllib.inflater;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import static com.asha.nightowllib.NightOwlUtil.checkNonNull;

/**
 * Created by hzqiujiadi on 15/11/7.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class LastChance2Inflater {
    private InjectedInflaterBase mInflater;

    protected LastChance2Inflater(InjectedInflaterBase inflater) {
        mInflater = inflater;
    }

    protected View lastChance2CreateView(View parent, String name, Context context, AttributeSet attrs){
        View view = null;
        Object[] tmpConstructorArgs = mInflater.getConstructorArgs();
        checkNonNull(tmpConstructorArgs,"LayoutInflater mConstructorArgs is null.");

        final Object lastContext = tmpConstructorArgs[0];
        tmpConstructorArgs[0] = context;
        try {
            if (-1 == name.indexOf('.')) {
                view = onCreateViewCompact(parent, name, attrs);
            } else {
                view = mInflater.createView(name, null, attrs);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            tmpConstructorArgs[0] = lastContext;
        }
        return view;
    }

    private View onCreateViewCompact(View parent,String name,AttributeSet attrs) throws ClassNotFoundException {
        View view = null;
        if ( mInflater instanceof InjectedInflaterV11 )
            view = mInflater.onCreateView( parent, name, attrs);
        else {
            view = mInflater.onCreateView( name, attrs);
        }
        return view;
    }
}
