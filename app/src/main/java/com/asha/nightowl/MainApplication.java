package com.asha.nightowl;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.asha.nightowl.custom.CardViewHandler;
import com.asha.nightowl.custom.CollapsingToolbarLayoutHandler;
import com.asha.nightowl.custom.OwlCustomTable;
import com.asha.nightowl.custom.TabLayoutHandler;
import com.asha.nightowl.custom.ToolbarHandler;
import com.asha.nightowllib.NightOwl;
import com.asha.nightowllib.observer.IOwlObserver;

/**
 * Created by hzqiujiadi on 15/11/6.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getSharedPreferences("NightOwlDemo",Activity.MODE_PRIVATE);
        int mode = preferences.getInt("mode",0);

        NightOwl.builder().subscribedBy(new SkinObserver()).defualt(mode).create();
        NightOwl.owlRegisterHandler(TabLayoutHandler.class, OwlCustomTable.OwlTabLayout.class);
        NightOwl.owlRegisterHandler(ToolbarHandler.class, OwlCustomTable.OwlToolbar.class);
        NightOwl.owlRegisterHandler(CollapsingToolbarLayoutHandler.class, OwlCustomTable.OwlCollapsingToolbarLayout.class);
        NightOwl.owlRegisterHandler(CardViewHandler.class,OwlCustomTable.OwlCardView.class);
    }

    public static class SkinObserver implements IOwlObserver {
        @Override
        public void onSkinChange(int mode, Activity activity) {
            SharedPreferences preferences = activity.getSharedPreferences("NightOwlDemo",
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("mode", mode);
            SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        }
    }
}
