package com.ssangwoo.medicationalarm.views.activities;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;

import com.ssangwoo.medicationalarm.R;

public abstract class BaseToolbarWithBackButtonActivity extends BaseActivity {

    Toolbar toolbar;

    @Override
    protected void setView() {
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(setToolbarTitleRes());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    protected abstract int setToolbarTitleRes();
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
