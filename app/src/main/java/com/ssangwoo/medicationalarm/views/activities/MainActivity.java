package com.ssangwoo.medicationalarm.views.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.controllers.MainFragmentAdapter;

public class MainActivity extends BaseActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void setView() {
        collapsingToolbarLayout.setTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(
                MainFragmentAdapter.newInstance(
                        this, getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);
        if(tabLayout.getTabCount() > 0) {
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_list);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_calendar);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_analysis);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == R.integer.request_edit_medicine) {
            if (resultCode == RESULT_OK) {
                viewPager.setAdapter(
                        MainFragmentAdapter.newInstance(
                                this, getSupportFragmentManager()));
            }
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        collapsingToolbarLayout = findViewById(R.id.main_collapsing_toolbar_layout);
        toolbar = findViewById(R.id.main_toolbar);
        viewPager = findViewById(R.id.main_view_pager);
        tabLayout = findViewById(R.id.main_tab_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_setting) {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar_action, menu);
        // MenuItem item = menu.findItem(R.id.action_setting);
        return super.onCreateOptionsMenu(menu);
    }
}
