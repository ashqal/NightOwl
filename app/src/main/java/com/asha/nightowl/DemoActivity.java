package com.asha.nightowl;

import android.content.res.Resources;
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
    private static final String TAG = "DemoActivity";

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        NightOwl.owlAfterCreate(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //toolbar.setPopupTheme(R.style.MyPopupMenu);
        //toolbar.setPopupTheme(R.style.MyPopupMenu_Night);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        PagerAdapter pa = new DemoPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pa);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
        // theme.applyStyle(R.style.AdditionThemeDay, true);
        // Log.e(TAG, String.format("%s %d", theme, R.style.AdditionThemeDay));
        //theme.applyStyle(R.style.AdditionThemeNight,true);
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
                SettingActivity.launch(this); return true;
            case R.id.action_change_day:
                //toolbar.getContext().getTheme().applyStyle(R.style.AdditionThemeDay,true);
                toolbar.setPopupTheme(R.style.MyPopupMenu);
                return true;

            case R.id.action_change_night:
                //toolbar.getContext().getTheme().applyStyle(R.style.AdditionThemeNight,true);
                toolbar.setPopupTheme(R.style.MyPopupMenu_Night);
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
