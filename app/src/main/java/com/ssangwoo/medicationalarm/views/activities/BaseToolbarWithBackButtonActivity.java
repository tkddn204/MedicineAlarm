package com.ssangwoo.medicationalarm.views.activities;

public abstract class BaseToolbarWithBackButtonActivity extends BaseToolbarActivity {

    @Override
    protected void setView() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
