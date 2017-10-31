package com.ssangwoo.medicationalarm.views.activities;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.WhenModel;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.Date;

public class EditMedicineActivity extends BaseToolbarWithBackButtonActivity
            implements CompoundButton.OnCheckedChangeListener {

    TextInputEditText editTitle, editDesc;
    TextInputEditText editDateFrom, editDateTo;

    CheckBox checkBoxMorning, checkBoxAfternoon, checkBoxDinner;

    LinearLayout morningAlarmContainer, afternoonAlarmContainer, dinnerAlarmContainer;
    CheckBox checkBoxMorningAlarm, checkBoxAfternoonAlarm, checkBoxDinnerAlarm;
    EditText editMorningAlarm, editAfternoonAlarm, editDinnerAlarm;

    Button buttonSubmit;

    @Override
    protected void setView() {
        super.setView();
        editDateFrom.setText(AppDateFormat.DATE_FROM.format(new Date()));
        editDateTo.setText(AppDateFormat.DATE_TO.format(new Date()));

        checkBoxMorning.setOnCheckedChangeListener(this);
        checkBoxAfternoon.setOnCheckedChangeListener(this);
        checkBoxDinner.setOnCheckedChangeListener(this);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhenModel whenModel = new WhenModel(
                        checkBoxMorning.isChecked(),
                        checkBoxAfternoon.isChecked(),
                        checkBoxDinner.isChecked());
                MedicineModel medicineModel = new MedicineModel(
                        editTitle.getText().toString(),
                        editDesc.getText().toString(),
                        whenModel);
                whenModel.save();
                medicineModel.save();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int visibleFlag = b ? View.VISIBLE : View.GONE;
        switch (compoundButton.getId()) {
            case R.id.checkbox_edit_morning:
                morningAlarmContainer.setVisibility(visibleFlag);
                break;
            case R.id.checkbox_edit_afternoon:
                afternoonAlarmContainer.setVisibility(visibleFlag);
                break;
            case R.id.checkbox_edit_dinner:
                dinnerAlarmContainer.setVisibility(visibleFlag);
                break;
//            case R.id.checkbox_edit_morning_alarm:
//                break;
//            case R.id.checkbox_edit_afternoon_alarm:
//                break;
//            case R.id.checkbox_edit_dinner_alarm:
//                break;
            default:
                break;
        }
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

        checkBoxMorning = findViewById(R.id.checkbox_edit_morning);
        checkBoxAfternoon = findViewById(R.id.checkbox_edit_afternoon);
        checkBoxDinner = findViewById(R.id.checkbox_edit_dinner);

        morningAlarmContainer = findViewById(R.id.morning_alarm_container);
        afternoonAlarmContainer = findViewById(R.id.afternoon_alarm_container);
        dinnerAlarmContainer = findViewById(R.id.dinner_alarm_container);
        checkBoxMorningAlarm = findViewById(R.id.checkbox_edit_morning_alarm);
        checkBoxAfternoonAlarm = findViewById(R.id.checkbox_edit_afternoon_alarm);
        checkBoxDinnerAlarm = findViewById(R.id.checkbox_edit_dinner_alarm);
        editMorningAlarm = findViewById(R.id.edit_morning_alarm);
        editAfternoonAlarm = findViewById(R.id.edit_afternoon_alarm);
        editDinnerAlarm = findViewById(R.id.edit_dinner_alarm);

        buttonSubmit = findViewById(R.id.button_edit_submit);
    }
}
