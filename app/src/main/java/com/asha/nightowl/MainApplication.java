package com.asha.nightowl;

import android.app.Application;

import com.asha.nightowl.custom.CollapsingToolbarLayoutHandler;
import com.asha.nightowl.custom.OwlCustomTable;
import com.asha.nightowl.custom.TabLayoutHandler;
import com.asha.nightowl.custom.ToolbarHandler;
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
        NightOwl.owlRegisterHandler(TabLayoutHandler.class, OwlCustomTable.OwlTabLayout.class);
        NightOwl.owlRegisterHandler(ToolbarHandler.class, OwlCustomTable.OwlToolbar.class);
        NightOwl.owlRegisterHandler(CollapsingToolbarLayoutHandler.class, OwlCustomTable.OwlCollapsingToolbarLayout.class);
    }
}
