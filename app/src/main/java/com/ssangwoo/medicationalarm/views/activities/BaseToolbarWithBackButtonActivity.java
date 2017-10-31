package com.ssangwoo.medicationalarm.views.activities;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;

public abstract class BaseToolbarWithBackButtonActivity extends BaseActivity {

    Toolbar toolbar;

    @Override
    protected void setView() {
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(setToolbarTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    protected abstract String setToolbarTitle();
    protected abstract int setToolbarViewId();
    protected abstract int setContentViewRes();

    @Override
    protected void initView() {
        setContentView(setContentViewRes());
        toolbar = findViewById(setToolbarViewId());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
