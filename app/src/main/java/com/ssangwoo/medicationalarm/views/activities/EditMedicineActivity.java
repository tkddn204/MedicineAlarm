package com.ssangwoo.medicationalarm.views.activities;

import com.ssangwoo.medicationalarm.R;

public class EditMedicineActivity extends BaseToolbarWithBackButtonActivity {

    @Override
    protected void setView() {
        super.setView();
    }

    @Override
    protected int setToolbarTitleRes() {
        return R.string.main_add;
    }

    @Override
    protected int setToolbarViewId() {
        return R.id.edit_toolbar;
    }

    @Override
    protected int setContentViewRes() {
        return R.layout.activity_edit_medicine;
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
