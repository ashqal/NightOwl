package com.asha.nightowllib.inflater;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.asha.nightowllib.NightOwl;
import com.asha.nightowllib.handler.ISkinHandler;
import com.asha.nightowllib.observer.IOwlObserver;

import java.lang.reflect.Field;

import static com.asha.nightowllib.NightOwlUtil.checkHandler;
import static com.asha.nightowllib.NightOwlUtil.checkViewCollected;
import static com.asha.nightowllib.NightOwlUtil.insertEmptyTag;
import static com.asha.nightowllib.handler.OwlHandlerManager.queryHandler;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class InjectedInflaterBase extends LayoutInflater {

    private static Field sConstructorArgsField;
    private static boolean sCheckConstructorArgsField;
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app."
    };

    private static final String TAG = "InjectLayoutInflater";

    protected InjectedInflaterBase(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return Factory4InjectedInflater.newInstance(this,newContext);
    }

    @Override protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        View v = null;
        for (String prefix : sClassPrefixList) {
            try {
                v = createView(name, prefix, attrs);
            } catch (ClassNotFoundException e) {
                // try to find class again
                // e.printStackTrace();
            }
        }
        if ( v == null ){
            v = super.onCreateView(name, attrs);
        }
        handleOnCreateView(v,name,attrs);
        return v;
    }

    @Override
    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        View v = super.onCreateView(parent, name, attrs);
        //Log.d(TAG,"onCreateView2:" + v);
        handleOnCreateView(v,name,attrs);
        return v;
    }

    public Object[] getConstructorArgs(){
        if ( !sCheckConstructorArgsField ){
            try {
                sConstructorArgsField = LayoutInflater.class.getDeclaredField("mConstructorArgs");
                sConstructorArgsField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            sCheckConstructorArgsField = true;
        }

        try {
            return (Object[]) sConstructorArgsField.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void handleOnCreateView(View view,String name,AttributeSet attrs){
        if ( view == null ) return;
        // check the view has been collected
        if ( checkViewCollected(view) ) return;

        // query the handler
        ISkinHandler handler = queryHandler(view.getClass());
        if ( !checkHandler(handler,view) ) return;

        int mode = NightOwl.owlCurrentMode();

        // do collect
        handler.collect(mode, view, view.getContext(), attrs);

        // if view is instanceof IOwlObserver
        // and not be collected
        if ( view instanceof IOwlObserver){
            // insert tag
            if (  !checkViewCollected(view) ) insertEmptyTag(view);

            // we can't get the activity here
            // beacuse the view.getContext may return ContextThemeWrapper
            // so we call with null
            ((IOwlObserver) view).onSkinChange( mode, null );
        }
    }

}
