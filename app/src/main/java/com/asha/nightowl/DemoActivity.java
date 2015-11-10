package com.asha.nightowl;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.asha.nightowl.fragments.ListViewFragment;
import com.asha.nightowl.fragments.RecyclerViewFragment;
import com.asha.nightowllib.NightOwl;

/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class DemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        NightOwl.owlAfterCreate(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        PagerAdapter pa = new DemoPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pa);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                SettingActivity.launch(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class DemoPagerAdapter extends FragmentPagerAdapter {

        private static final String[] titles = {"recyclerview","listview"};

        public DemoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                 case 0: return RecyclerViewFragment.newInstance();
                 case 1:
                default: return ListViewFragment.newInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
