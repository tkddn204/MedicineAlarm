package com.ssangwoo.medicationalarm.views.activities;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.MedicineModel_Table;
import com.ssangwoo.medicationalarm.models.WhenModel;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.Date;

public class EditMedicineActivity extends BaseToolbarWithBackButtonActivity
            implements CompoundButton.OnCheckedChangeListener,
                       View.OnClickListener {

    TextInputEditText editTitle, editDesc;
    TextInputEditText editDateFrom, editDateTo;

    CheckBox checkBoxBreakfast, checkBoxLunch, checkBoxDinner;

    LinearLayout breakfastAlarmContainer, lunchAlarmContainer, dinnerAlarmContainer;
    CheckBox checkBoxBreakfastAlarm, checkBoxLunchAlarm, checkBoxDinnerAlarm;
    EditText editBreakfastAlarm, editLunchAlarm, editDinnerAlarm;

    Button buttonSubmit;

    MedicineModel medicineModel;

    @Override
    protected void setView() {
        super.setView();
        if(getIntent().hasExtra("edit_medicine_id")) {
            int medicineId = getIntent().getIntExtra("edit_medicine_id", -1);
            medicineModel =
                    new Select().from(MedicineModel.class)
                            .where(MedicineModel_Table.id.eq(medicineId)).querySingle();
            WhenModel whenModel = medicineModel.getWhen();
            editTitle.setText(medicineModel.getTitle());
            editDesc.setText(medicineModel.getDescription());
            editDateFrom.setText(AppDateFormat.DATE_FROM.format(medicineModel.getDateFrom()));
            editDateTo.setText(AppDateFormat.DATE_TO.format(medicineModel.getDateTo()));

            checkBoxBreakfast.setChecked(whenModel.isBreakfast());
            checkBoxLunch.setChecked(whenModel.isLunch());
            checkBoxDinner.setChecked(whenModel.isDinner());

            checkBoxBreakfastAlarm.setChecked(whenModel.isBreakfastAlarm());
            checkBoxLunchAlarm.setChecked(whenModel.isLunch());
            checkBoxDinnerAlarm.setChecked(whenModel.isDinner());

            int visibleFlag = checkBoxBreakfastAlarm.isChecked() ? View.VISIBLE : View.GONE;
            breakfastAlarmContainer.setVisibility(visibleFlag);
            visibleFlag = checkBoxLunchAlarm.isChecked() ? View.VISIBLE : View.GONE;
            lunchAlarmContainer.setVisibility(visibleFlag);
            visibleFlag = checkBoxDinnerAlarm.isChecked() ? View.VISIBLE : View.GONE;
            dinnerAlarmContainer.setVisibility(visibleFlag);
        } else {
            editDateFrom.setText(AppDateFormat.DATE_FROM.format(new Date()));
            editDateTo.setText(AppDateFormat.DATE_TO.format(AppDateFormat.DATE_AFTER_SEVEN_DAYS));
        }


        checkBoxBreakfast.setOnCheckedChangeListener(this);
        checkBoxLunch.setOnCheckedChangeListener(this);
        checkBoxDinner.setOnCheckedChangeListener(this);

        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int visibleFlag = b ? View.VISIBLE : View.GONE;
        switch (compoundButton.getId()) {
            case R.id.checkbox_edit_breakfast:
                breakfastAlarmContainer.setVisibility(visibleFlag);
                break;
            case R.id.checkbox_edit_lunch:
                lunchAlarmContainer.setVisibility(visibleFlag);
                break;
            case R.id.checkbox_edit_dinner:
                dinnerAlarmContainer.setVisibility(visibleFlag);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        WhenModel whenModel = new WhenModel(
                checkBoxBreakfast.isChecked(),
                checkBoxLunch.isChecked(),
                checkBoxDinner.isChecked(),
                checkBoxBreakfastAlarm.isChecked(),
                checkBoxLunchAlarm.isChecked(),
                checkBoxDinnerAlarm.isChecked());
        if(getIntent().hasExtra("edit_medicine_id")) {
            medicineModel.setTitle(editTitle.getText().toString());
            medicineModel.setDescription(editDesc.getText().toString());
            medicineModel.getWhen().delete();
            medicineModel.setWhen(whenModel);
            medicineModel.update();
        } else {
            medicineModel = new MedicineModel(
                    editTitle.getText().toString(),
                    editDesc.getText().toString(),
                    whenModel);
            medicineModel.save();
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected String setToolbarTitle() {
        return getString(R.string.main_add);
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
        editTitle = findViewById(R.id.text_input_edit_title);
        editDesc = findViewById(R.id.text_input_edit_desc);
        editDateFrom = findViewById(R.id.text_input_edit_date_from);
        editDateTo = findViewById(R.id.text_input_edit_date_to);

        checkBoxBreakfast = findViewById(R.id.checkbox_edit_breakfast);
        checkBoxLunch = findViewById(R.id.checkbox_edit_lunch);
        checkBoxDinner = findViewById(R.id.checkbox_edit_dinner);

        breakfastAlarmContainer = findViewById(R.id.breakfast_alarm_container);
        lunchAlarmContainer = findViewById(R.id.lunch_alarm_container);
        dinnerAlarmContainer = findViewById(R.id.dinner_alarm_container);
        checkBoxBreakfastAlarm = findViewById(R.id.checkbox_edit_breakfast_alarm);
        checkBoxLunchAlarm = findViewById(R.id.checkbox_edit_lunch_alarm);
        checkBoxDinnerAlarm = findViewById(R.id.checkbox_edit_dinner_alarm);
        editBreakfastAlarm = findViewById(R.id.edit_breakfast_alarm);
        editLunchAlarm = findViewById(R.id.edit_lunch_alarm);
        editDinnerAlarm = findViewById(R.id.edit_dinner_alarm);

        buttonSubmit = findViewById(R.id.button_edit_submit);
    }
}
