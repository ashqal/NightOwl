package com.asha.nightowllib.handler;

import android.view.View;

import com.asha.nightowllib.handler.ISkinHandler;
import com.asha.nightowllib.handler.annotations.OwlHandle;

import java.util.HashMap;

import static com.asha.nightowllib.NightOwlUtil.newInstanceSafely;

/**
 * Created by hzqiujiadi on 15/11/8.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class OwlHandlerManager {
    private static HashMap<Class,ISkinHandler> sHandlers = new HashMap<>();
    private static HashMap<Class,Class<? extends ISkinHandler>> sHandlerTable = new HashMap<>();

    // TODO: 15/11/5 impl it later.
    private static Class<ISkinHandler> generateHandler() {
        return null;
    }

    public static void registerView(Class<? extends View> clz){
        sHandlerTable.put(clz, generateHandler());
    }

    public static void registerHandler(Class key, Class<? extends ISkinHandler> clz){
        sHandlerTable.put(key,clz);
    }

    public static void registerHandler(Class<? extends ISkinHandler> clz){
        OwlHandle owlHandle = clz.getAnnotation(OwlHandle.class);
        if ( owlHandle != null ){
            Class<? extends View>[] keys = owlHandle.value();
            for ( Class<? extends View> key : keys )
                registerHandler(key,clz);
        }
    }

    public static ISkinHandler queryHandler(Class clz) {
        Class<? extends ISkinHandler> handlerClz = sHandlerTable.get(clz);
        // if handlerClz == null
        // look for superclass's handlerClz
        // it will be end when meet View.class
        while( handlerClz == null ){
            clz = clz.getSuperclass();
            handlerClz = sHandlerTable.get(clz);
        }
        // query handler now
        ISkinHandler skinHandler = sHandlers.get(handlerClz);
        if ( skinHandler == null ) {
            skinHandler = newInstanceSafely(handlerClz);
            sHandlers.put(handlerClz, skinHandler);
        }
        return skinHandler;
    }

}
