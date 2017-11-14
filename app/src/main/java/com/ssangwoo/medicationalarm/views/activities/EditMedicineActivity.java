package com.ssangwoo.medicationalarm.views.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.controllers.MedicineAlarmController;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.MedicineModel_Table;
import com.ssangwoo.medicationalarm.models.WhenModel;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.Calendar;
import java.util.Date;

public class EditMedicineActivity extends BaseToolbarWithBackButtonActivity
        implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {

    TextInputEditText editTitle, editDesc;
    TextView textEditDateFrom, textEditDateTo;

    CheckBox checkBoxBreakfast, checkBoxLunch, checkBoxDinner;

    LinearLayout breakfastAlarmContainer, lunchAlarmContainer, dinnerAlarmContainer;
    CheckBox checkBoxBreakfastAlarm, checkBoxLunchAlarm, checkBoxDinnerAlarm;
    TextView textEditBreakfastAlarmTime, textEditLunchAlarmTime, textEditDinnerAlarmTime;

    Button buttonSubmit;

    MedicineModel medicineModel;

    Date dateFrom = new Date();
    Date dateTo = new Date(AppDateFormat.DATE_AFTER_SEVEN_DAYS);
    Date breakfastAlarmTime, lunchAlarmTime, dinnerAlarmTime;

    @Override
    protected void setView() {
        super.setView();
        if (getIntent().hasExtra("edit_medicine_id")) {
            int medicineId = getIntent().getIntExtra("edit_medicine_id", -1);
            medicineModel = new Select().from(MedicineModel.class)
                            .where(MedicineModel_Table.id.eq(medicineId)).querySingle();
            WhenModel whenModel = medicineModel.getWhen();
            editTitle.setText(medicineModel.getTitle());
            editDesc.setText(medicineModel.getDescription());

            textEditDateFrom.setText(
                    AppDateFormat.DATE_FROM.format(medicineModel.getDateFrom()));
            textEditDateTo.setText(
                    AppDateFormat.DATE_TO.format(medicineModel.getDateTo()));

            checkBoxBreakfast.setChecked(whenModel.isBreakfast());
            checkBoxLunch.setChecked(whenModel.isLunch());
            checkBoxDinner.setChecked(whenModel.isDinner());

            int visibleFlag = checkBoxBreakfast.isChecked() ? View.VISIBLE : View.GONE;
            breakfastAlarmContainer.setVisibility(visibleFlag);
            visibleFlag = checkBoxLunch.isChecked() ? View.VISIBLE : View.GONE;
            lunchAlarmContainer.setVisibility(visibleFlag);
            visibleFlag = checkBoxDinner.isChecked() ? View.VISIBLE : View.GONE;
            dinnerAlarmContainer.setVisibility(visibleFlag);

            checkBoxBreakfastAlarm.setChecked(whenModel.isBreakfastAlarm());
            checkBoxLunchAlarm.setChecked(whenModel.isLunchAlarm());
            checkBoxDinnerAlarm.setChecked(whenModel.isDinnerAlarm());

            breakfastAlarmTime = whenModel.getBreakfastAlarm();
            lunchAlarmTime = whenModel.getLunchAlarm();
            dinnerAlarmTime = whenModel.getDinnerAlarm();
        } else {
            textEditDateFrom.setText(AppDateFormat.DATE_FROM.format(dateFrom));
            textEditDateTo.setText(AppDateFormat.DATE_TO.format(dateTo));

            breakfastAlarmTime = editAlarmTime(7, 30);
            lunchAlarmTime = editAlarmTime(12, 30);
            dinnerAlarmTime = editAlarmTime(18, 30);
        }
        textEditBreakfastAlarmTime.setText(
                AppDateFormat.ALARM_TIME.format(breakfastAlarmTime));
        textEditLunchAlarmTime.setText(
                AppDateFormat.ALARM_TIME.format(lunchAlarmTime));
        textEditDinnerAlarmTime.setText(
                AppDateFormat.ALARM_TIME.format(dinnerAlarmTime));

        textEditDateFrom.setOnClickListener(this);
        textEditDateTo.setOnClickListener(this);

        checkBoxBreakfast.setOnCheckedChangeListener(this);
        checkBoxLunch.setOnCheckedChangeListener(this);
        checkBoxDinner.setOnCheckedChangeListener(this);

        textEditBreakfastAlarmTime.setOnClickListener(this);
        textEditLunchAlarmTime.setOnClickListener(this);
        textEditDinnerAlarmTime.setOnClickListener(this);

        buttonSubmit.setOnClickListener(this);
    }

    private Date editWhenDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    private Date editAlarmTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
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
        if (view.getId() == R.id.text_edit_date_from) {
            onClickEditDate(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    dateFrom = editWhenDate(year, month, day);
                    textEditDateFrom.setText(
                            AppDateFormat.DATE_FROM.format(dateFrom));
                }
            });
        } else if (view.getId() == R.id.text_edit_date_to) {
            onClickEditDate(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    dateTo = editWhenDate(year, month, day);
                    textEditDateTo.setText(
                            AppDateFormat.DATE_TO.format(dateTo));
                }
            });
        } else if (view.getId() == R.id.text_edit_breakfast_alarm_time) {
            onClickEditTime(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    breakfastAlarmTime = editAlarmTime(hour, minute);
                    textEditBreakfastAlarmTime.setText(
                            AppDateFormat.ALARM_TIME.format(breakfastAlarmTime));
                }
            });
        } else if (view.getId() == R.id.text_edit_lunch_alarm_time) {
            onClickEditTime(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    lunchAlarmTime = editAlarmTime(hour, minute);
                    textEditLunchAlarmTime.setText(
                            AppDateFormat.ALARM_TIME.format(lunchAlarmTime));
                }
            });
        } else if (view.getId() == R.id.text_edit_dinner_alarm_time) {
            onClickEditTime(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    dinnerAlarmTime = editAlarmTime(hour, minute);
                    textEditDinnerAlarmTime.setText(
                            AppDateFormat.ALARM_TIME.format(dinnerAlarmTime));
                }
            });
        } else if (view.getId() == R.id.button_edit_submit) {
            WhenModel whenModel = new WhenModel(
                    checkBoxBreakfast.isChecked(),
                    checkBoxLunch.isChecked(),
                    checkBoxDinner.isChecked(),
                    checkBoxBreakfastAlarm.isChecked(),
                    checkBoxLunchAlarm.isChecked(),
                    checkBoxDinnerAlarm.isChecked(),
                    breakfastAlarmTime, lunchAlarmTime, dinnerAlarmTime);
            if (getIntent().hasExtra("edit_medicine_id")) {
                medicineModel.setTitle(editTitle.getText().toString());
                medicineModel.setDescription(editDesc.getText().toString());
                medicineModel.getWhen().delete();
                medicineModel.setWhen(whenModel);
                medicineModel.setModifiedDate(new Date());
                medicineModel.update();
            } else {
                medicineModel = new MedicineModel(
                        editTitle.getText().toString(),
                        editDesc.getText().toString(),
                        dateFrom, dateTo, whenModel);
                medicineModel.save();
            }

            MedicineAlarmController medicineAlarmController
                    = new MedicineAlarmController(getApplicationContext());
            if(checkBoxBreakfast.isChecked() && checkBoxBreakfastAlarm.isChecked()) {
                medicineAlarmController.startAlarm(breakfastAlarmTime.getTime());
            }
//            if(checkBoxLunch.isChecked() && checkBoxLunchAlarm.isChecked()) {
//                medicineAlarmController.startAlarm(lunchAlarmTime.getTime());
//            }
//            if(checkBoxDinner.isChecked() && checkBoxDinnerAlarm.isChecked()) {
//                medicineAlarmController.startAlarm(dinnerAlarmTime.getTime());
//            }
            setResult(RESULT_OK);
            finish();
        }
    }

    private void onClickEditTime(TimePickerDialog.OnTimeSetListener timeSetListener) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this, timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    private void onClickEditDate(DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar calendar = Calendar.getInstance();
        Context context;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            context = new ContextThemeWrapper(
                    this, android.R.style.Theme_Holo_Light_Dialog);
        } else {
            context = this;
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    protected String setToolbarTitle() {
        return getIntent().hasExtra("edit_medicine_id") ?
                getString(R.string.main_edit) : getString(R.string.main_add);
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
        textEditDateFrom = findViewById(R.id.text_edit_date_from);
        textEditDateTo = findViewById(R.id.text_edit_date_to);

        checkBoxBreakfast = findViewById(R.id.checkbox_edit_breakfast);
        checkBoxLunch = findViewById(R.id.checkbox_edit_lunch);
        checkBoxDinner = findViewById(R.id.checkbox_edit_dinner);

        breakfastAlarmContainer = findViewById(R.id.breakfast_alarm_container);
        lunchAlarmContainer = findViewById(R.id.lunch_alarm_container);
        dinnerAlarmContainer = findViewById(R.id.dinner_alarm_container);

        checkBoxBreakfastAlarm = findViewById(R.id.checkbox_edit_breakfast_alarm);
        checkBoxLunchAlarm = findViewById(R.id.checkbox_edit_lunch_alarm);
        checkBoxDinnerAlarm = findViewById(R.id.checkbox_edit_dinner_alarm);

        textEditBreakfastAlarmTime = findViewById(R.id.text_edit_breakfast_alarm_time);
        textEditLunchAlarmTime = findViewById(R.id.text_edit_lunch_alarm_time);
        textEditDinnerAlarmTime = findViewById(R.id.text_edit_dinner_alarm_time);

        buttonSubmit = findViewById(R.id.button_edit_submit);
    }
}
