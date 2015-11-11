package com.asha.nightowl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.asha.nightowllib.NightOwl;

public class DetailActivity extends AppCompatActivity {

    public static void launch(Context context){
        Intent i = new Intent(context,DetailActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        NightOwl.owlAfterCreate(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: this.finish(); return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
