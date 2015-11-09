package com.asha.nightowl;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;

import com.asha.demohelperlib.DemoHelperActivity;
import com.asha.nightowllib.NightOwl;

public class MainActivity extends DemoHelperActivity {

    public static final int sDefualtMode = 0;
    private int mMode = sDefualtMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.inject(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onViewCreated() {
        addButton("change skin", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMode++;
                mMode %= 2;
                NightOwl.refreshSkin( mMode, MainActivity.this );
            }
        });
        addButton("add Fragment", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MainFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.dh_main_container, fragment, "test")
                        .commit();
            }
        });
        addButton("show dialog fragment", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = MainDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "MainDialogFragment");
            }
        });

        addButton("show dialog", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.show(MainActivity.this);
            }
        });
    }
}
