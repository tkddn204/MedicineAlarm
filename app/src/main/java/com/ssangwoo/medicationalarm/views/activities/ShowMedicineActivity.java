package com.ssangwoo.medicationalarm.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.controllers.MedicineRecyclerViewAdapter;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.MedicineModel_Table;
import com.ssangwoo.medicationalarm.models.WhenModel;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.List;

public class ShowMedicineActivity extends BaseToolbarWithBackButtonActivity {

    TextView showTitle, showDesc;
    TextView showDateFrom, showDateTo;
    TextView showBreakfast, showLunch, showDinner;
    TextView showBreakfastAlarm, showLunchAlarm, showDinnerAlarm;

    MedicineModel medicineModel;

    @Override
    protected void setView() {
        super.setView();
        int medicineId = getIntent().getIntExtra("medicine_id", 0);
        medicineModel = new Select()
                .from(MedicineModel.class)
                .where(MedicineModel_Table.id.eq(medicineId))
                .querySingle();

        if(medicineModel == null) {
            return;
        }

        WhenModel when = medicineModel.getWhen();

        showTitle.setText(medicineModel.getTitle());
        showDesc.setText(medicineModel.getDescription());
        showDateFrom.setText(AppDateFormat.DATE_FROM.format(medicineModel.getDateFrom()));
        showDateTo.setText(AppDateFormat.DATE_TO.format(medicineModel.getDateTo()));
        showBreakfast.setText(Boolean.toString(when.isBreakfast()));
        showLunch.setText(Boolean.toString(when.isLunch()));
        showDinner.setText(Boolean.toString(when.isDinner()));
        showBreakfastAlarm.setText(Boolean.toString(when.isBreakfastAlarm()));
        showLunchAlarm.setText(Boolean.toString(when.isLunchAlarm()));
        showDinnerAlarm.setText(Boolean.toString(when.isDinnerAlarm()));
    }

    @Override
    protected void initView() {
        super.initView();
        showTitle = findViewById(R.id.text_show_title);
        showDesc = findViewById(R.id.text_show_desc);
        showDateFrom = findViewById(R.id.text_show_date_from);
        showDateTo = findViewById(R.id.text_show_date_to);
        showBreakfast = findViewById(R.id.text_show_breakfast);
        showLunch = findViewById(R.id.text_show_lunch);
        showDinner = findViewById(R.id.text_show_dinner);
        showBreakfastAlarm = findViewById(R.id.text_show_breakfast_alarm);
        showLunchAlarm = findViewById(R.id.text_show_lunch_alarm);
        showDinnerAlarm = findViewById(R.id.text_show_dinner_alarm);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_show_edit) {
            Intent intent = new Intent(this, EditMedicineActivity.class);
            intent.putExtra("edit_medicine_id", medicineModel.getId());
            startActivityForResult(intent, getResources().getInteger(R.integer.request_edit_medicine));
        } else if (item.getItemId() == R.id.action_show_delete){
            new AlertDialog.Builder(this)
                    .setTitle(medicineModel.getTitle() + " 삭제")
                    .setMessage(getString(R.string.show_delete_message))
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new Delete().from(MedicineModel.class)
                                    .where(MedicineModel_Table.id.eq(medicineModel.getId()))
                                    .execute();
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
