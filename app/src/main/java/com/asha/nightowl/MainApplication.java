package com.asha.nightowl;

import android.app.Application;

import com.asha.nightowllib.NightOwl;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class MainApplication extends Application {
    private static int sDefualtMode = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        NightOwl.builder().defualt(MainApplication.sDefualtMode).create();
    }
}
