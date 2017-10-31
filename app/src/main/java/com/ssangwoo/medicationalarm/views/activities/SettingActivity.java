package com.ssangwoo.medicationalarm.views.activities;


import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import com.ssangwoo.medicationalarm.R;

public class SettingActivity extends BaseActivity {

    Toolbar toolbar;

    @Override
    protected void setView() {
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.main_setting);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
    }

    @Override
    protected void setOperationForView() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);
        toolbar = findViewById(R.id.setting_toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
