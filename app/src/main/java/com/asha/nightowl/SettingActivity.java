package com.asha.nightowl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.asha.nightowllib.NightOwl;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        NightOwl.owlAfterCreate(this);

        View v = findViewById(R.id.button);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NightOwl.owlNewDress(SettingActivity.this);
            }
        });

        if ( getSupportActionBar() != null )
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void launch(Context context){
        Intent i = new Intent(context,SettingActivity.class);
        context.startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: this.finish(); return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
    }
}
