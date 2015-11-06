package com.asha.nightowl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.asha.demohelperlib.DemoHelperActivity;
import com.asha.nightowllib.NightOwl;

public class MainActivity extends DemoHelperActivity {


    static final String data = "ashqal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewCreated() {
        addButton("add Fragment", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MainFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.dh_main_container, fragment, "test")
                        .commit();
            }
        });

        addButton("modify static final", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow();
                LayoutInflater inflater1 = getLayoutInflater();
                LayoutInflater inflater2 = LayoutInflater.from(MainActivity.this);

                DHLog("before:%s", data);
                DHLog("after:%s", data);
            }
        });
    }


}
