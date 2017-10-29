package com.ssangwoo.medicationalarm.views.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.controllers.MainFragmentAdapter;

public class MainActivity extends BaseActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void setView() {
        viewPager.setAdapter(
                MainFragmentAdapter.newInstance(
                        this, getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void setOperationForView() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.main_view_pager);
        tabLayout = findViewById(R.id.main_tab_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        return super.onCreateOptionsMenu(menu);
    }
}
