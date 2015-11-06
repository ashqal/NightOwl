package com.asha.nightowllib;

import android.util.Log;
import android.view.View;

import com.asha.nightowllib.handler.ISkinHandler;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class NightOwlUtil {
    public static final int NIGHT_OWL_VIEW_TAG = (2 << 24) | (1 << 23);
    private static final String TAG = "NightOwlUtil";

    public static void checkNonNull(Object obj,String msg){
        if ( obj == null ) throw new NullPointerException(msg);
    }

    public static boolean checkHandler(ISkinHandler handler,View view){
        if ( handler == null ){
            Log.e(TAG, "Can't find handler of clz:" + view.getClass().getName());
            return false;
        }
        return true;
    }

    public static boolean checkViewCollected(View view){
        return view.getTag( NIGHT_OWL_VIEW_TAG ) != null;
    }

    public static void insertSkinContext(View view,Object obj){
        view.setTag(NIGHT_OWL_VIEW_TAG,obj);
    }

}
