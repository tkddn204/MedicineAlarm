package com.ssangwoo.medicationalarm.views.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.controllers.SettingRecyclerViewAdapter;

import java.util.ArrayList;

public class SettingActivity extends BaseToolbarWithBackButtonActivity {

    RecyclerView settingRecyclerView;

    @Override
    protected void setView() {
        super.setView();
        settingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> settingList = new ArrayList<>();
        settingList.add("알람");
        settingList.add("알람표시방법");
        settingRecyclerView.setAdapter(
                new SettingRecyclerViewAdapter(this, settingList));
    }

    @Override
    protected String setToolbarTitle() {
        return getString(R.string.main_setting);
    }

    @Override
    protected int setToolbarViewId() {
        return R.id.setting_toolbar;
    }

    @Override
    protected int setContentViewRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        settingRecyclerView = findViewById(R.id.setting_recycler_view);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
