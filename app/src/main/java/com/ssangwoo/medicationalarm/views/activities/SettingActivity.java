package com.ssangwoo.medicationalarm.views.activities;

import com.ssangwoo.medicationalarm.R;

public class SettingActivity extends BaseToolbarWithBackButtonActivity {

    @Override
    protected void setView() {
        super.setView();
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
