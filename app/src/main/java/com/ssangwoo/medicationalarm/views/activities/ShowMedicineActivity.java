package com.ssangwoo.medicationalarm.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.notifications.AlarmNotification;
import com.ssangwoo.medicationalarm.controllers.AlarmRecyclerViewAdapter;
import com.ssangwoo.medicationalarm.lib.RecyclerViewEmptySupport;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.util.AppDateFormat;
import com.ssangwoo.medicationalarm.views.dialogs.EditAlarmTimeDialog;

public class ShowMedicineActivity extends BaseToolbarWithBackButtonActivity {

    private TextView textTitle, textDesc;
    private TextView textDate;
    private RecyclerViewEmptySupport showAlarmRecyclerView;
    private AlarmRecyclerViewAdapter alarmAdapter;
    private RelativeLayout recyclerViewEmptyContainer;

    private Medicine medicine;

    @Override
    protected void setView() {
        super.setView();
        final int medicineId = getIntent().getIntExtra("medicine_id", 0);
        medicine = AppDatabaseDAO.selectMedicine(medicineId);
        textTitle.setText(medicine.getTitle());
        if(medicine.getTitle().equals("")) {
            textTitle.setText("-");
        }
        textDesc.setText(medicine.getDescription());
        if(medicine.getDescription().equals("")) {
            textDesc.setText("-");
        }
        String dateString = AppDateFormat.buildDateString(medicine, "\n");
        textDate.setText(dateString);

        showAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showAlarmRecyclerView.setEmptyView(recyclerViewEmptyContainer);

        alarmAdapter = new AlarmRecyclerViewAdapter(medicine.getAlarmList());
        alarmAdapter.setListener(new FlowContentObserver.OnModelStateChangedListener() {
            @Override
            public void onModelStateChanged(@Nullable Class<?> table, BaseModel.Action action,
                                            @NonNull SQLOperator[] primaryKeyValues) {
                alarmAdapter.setDataList(AppDatabaseDAO.selectAlarmList(medicineId));
                alarmAdapter.notifyDataSetChanged();
            }
        });
        showAlarmRecyclerView.setAdapter(alarmAdapter);

        recyclerViewEmptyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditAlarmTimeDialog(ShowMedicineActivity.this, medicine).make();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_show_edit) {
            Intent intent = new Intent(this, EditMedicineActivity.class);
            intent.putExtra("edit_medicine_id", medicine.getId());
            startActivityForResult(intent, getResources().getInteger(R.integer.request_edit_medicine));
        } else if (item.getItemId() == R.id.action_show_delete){
            new AlertDialog.Builder(this)
                    .setTitle(medicine.getTitle() + " 삭제")
                    .setMessage(getString(R.string.show_delete_message))
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int medicineId = medicine.getId();
                            AppDatabaseDAO.deleteMedicine(medicineId);
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
                medicine = AppDatabaseDAO.selectMedicine(medicine.getId());
                setView();
                setResult(RESULT_OK);
            }
        }
    }

    @Override
    protected void initView() {
        super.initView();

        if(getIntent().hasExtra("notification_id")) {
            new AlarmNotification(getApplicationContext())
                    .cancel(getIntent().getIntExtra("notification_id", -1));
        }

        textTitle = findViewById(R.id.text_medicine_title);
        textDesc = findViewById(R.id.text_medicine_desc);
        textDate = findViewById(R.id.text_medicine_date);
        showAlarmRecyclerView = findViewById(R.id.show_alarm_recycler_view);
        recyclerViewEmptyContainer = findViewById(R.id.alarm_recycler_empty_container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_toolbar_action, menu);
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
