package com.ssangwoo.medicationalarm.views.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.controllers.MainFragmentAdapter;

public class MainActivity extends BaseActivity {

    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    FloatingActionButton floatingActionButton;

    @Override
    protected void setView() {
        collapsingToolbarLayout.setTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0 && tabLayout.getSelectedTabPosition() == 0) {
                    floatingActionButton.show();
                } else {
                    floatingActionButton.hide();
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int requestCode = getResources().getInteger(R.integer.request_edit_medicine);
                startActivityForResult(
                        new Intent(getApplicationContext(), EditMedicineActivity.class),
                        requestCode);
            }
        });

        viewPager.setAdapter(
                MainFragmentAdapter.newInstance(
                        this, getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        setUpTabIcons();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    floatingActionButton.show();
                } else {
                    floatingActionButton.hide();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpTabIcons() {
        if(tabLayout.getTabCount() > 0) {
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_list);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_calendar);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_analysis);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == getResources().getInteger(R.integer.request_edit_medicine)) {
            if (resultCode == RESULT_OK) {
                viewPager.getAdapter().notifyDataSetChanged();
                setUpTabIcons();
            }
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        appBarLayout = findViewById(R.id.main_app_bar_layout);
        collapsingToolbarLayout = findViewById(R.id.main_collapsing_toolbar_layout);
        toolbar = findViewById(R.id.main_toolbar);
        viewPager = findViewById(R.id.main_view_pager);
        tabLayout = findViewById(R.id.main_tab_layout);
        floatingActionButton = findViewById(R.id.medicine_floating_action_button);
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
