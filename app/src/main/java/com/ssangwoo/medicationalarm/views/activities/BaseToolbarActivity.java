package com.ssangwoo.medicationalarm.views.activities;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;

/**
 * Created by ssangwoo on 2017-11-17.
 */

public abstract class BaseToolbarActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void setView() {
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(setToolbarTitle());
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
}
