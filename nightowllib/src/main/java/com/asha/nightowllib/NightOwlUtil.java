package com.asha.nightowllib;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class NightOwlUtil {
    public static void checkNonNull(Object obj,String msg){
        if ( obj == null ) throw new NullPointerException(msg);
    }
}
