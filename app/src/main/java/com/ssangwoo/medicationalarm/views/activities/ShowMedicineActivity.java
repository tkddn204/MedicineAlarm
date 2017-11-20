package com.ssangwoo.medicationalarm.views.activities;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.AlarmController;
import com.ssangwoo.medicationalarm.controllers.AlarmRecyclerViewAdapter;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.Calendar;
import java.util.List;

public class ShowMedicineActivity extends BaseToolbarWithBackButtonActivity {

    TextView textTitle, textDesc;
    TextView textDate;
    RelativeLayout alarmSelectContainer;
    TextView textKindOfAlarm;
    RecyclerView showAlarmRecyclerView;
    AlarmRecyclerViewAdapter alarmAdapter;
    ImageView imageAddAlarm;

    Medicine medicine;

    @Override
    protected void setView() {
        super.setView();
        final int medicineId = getIntent().getIntExtra("medicine_id", 0);
        medicine = AppDatabaseDAO.selectMedicine(medicineId);
        textTitle.setText(medicine.getTitle());
        textDesc.setText(medicine.getDescription());
        String dateString = AppDateFormat.DATE_FROM.format(medicine.getDateFrom())
                + " " + AppDateFormat.DATE_TO.format(medicine.getDateTo());
        textDate.setText(dateString);

        showAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        alarmAdapter = new AlarmRecyclerViewAdapter(this, medicine.getAlarmList());
        showAlarmRecyclerView.setAdapter(alarmAdapter);
        imageAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(ShowMedicineActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                        Calendar changeCalendar = Calendar.getInstance();
                        changeCalendar.set(Calendar.HOUR_OF_DAY, hour);
                        changeCalendar.set(Calendar.MINUTE, minutes);
                        changeCalendar.set(Calendar.SECOND, 0);
                        Alarm alarm = new Alarm(medicine,
                                "알람", changeCalendar.getTime(), true);
                        alarm.save();
                        new AlarmController(ShowMedicineActivity.this)
                                .startAlarm(alarm.getDate().getTime(),
                                        medicine.getId(), alarm.getId());
                        updateAlarmList();
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                   calendar.get(Calendar.MINUTE), false).show();
            }
        });
    }

    private void updateAlarmList() {
        List<Alarm> alarmList =
                AppDatabaseDAO.selectMedicine(medicine.getId()).getAlarmList();
        alarmAdapter = new AlarmRecyclerViewAdapter(this, alarmList);
        showAlarmRecyclerView.setAdapter(alarmAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_show_edit) {
            Intent intent = new Intent(this, MedicineEditActivity.class);
            intent.putExtra("edit_medicine_id", medicine.getId());
            startActivityForResult(intent, getResources().getInteger(R.integer.request_edit_medicine));
        } else if (item.getItemId() == R.id.action_show_delete){
            new AlertDialog.Builder(this)
                    .setTitle(medicine.getTitle() + " 삭제")
                    .setMessage(getString(R.string.show_delete_message))
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AppDatabaseDAO.deleteMedicine(medicine.getId());
                            setResult(RESULT_OK);
                            finish();
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getResources().getInteger(R.integer.request_edit_medicine)) {
            if (resultCode == RESULT_OK) {
                setView();
                setResult(RESULT_OK);
            }
        }
    }

    @Override
    protected void initView() {
        super.initView();
        textTitle = findViewById(R.id.text_medicine_title);
        textDesc = findViewById(R.id.text_medicine_desc);
        textDate = findViewById(R.id.text_medicine_date);
        alarmSelectContainer = findViewById(R.id.alarm_select_container);
        textKindOfAlarm = findViewById(R.id.text_kind_of_alarm);
        showAlarmRecyclerView = findViewById(R.id.show_alarm_recycler_view);
        imageAddAlarm = findViewById(R.id.image_add_alarm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_toolbar_action, menu);
        // MenuItem item = menu.findItem(R.id.action_setting);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected String setToolbarTitle() {
        return getString(R.string.show_toolbar_title);
    }

    @Override
    protected int setToolbarViewId() {
        return R.id.show_toolbar;
    }

    @Override
    protected int setContentViewRes() {
        return R.layout.activity_show_medicine;
    }
}
