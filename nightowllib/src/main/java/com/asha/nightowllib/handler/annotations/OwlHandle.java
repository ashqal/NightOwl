package com.asha.nightowllib.handler.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hzqiujiadi on 15/11/8.
 * hzqiujiadi ashqalcn@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OwlHandle {
    Class<? extends View>[] value();
}
