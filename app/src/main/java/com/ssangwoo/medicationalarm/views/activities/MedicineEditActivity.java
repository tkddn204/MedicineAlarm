package com.ssangwoo.medicationalarm.views.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.models.Medicine_Table;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.Calendar;
import java.util.Date;

public class MedicineEditActivity extends BaseToolbarWithBackButtonActivity
        implements View.OnClickListener {

    EditText editTitle, editDesc;
    TextView textEditDateFrom, textEditDateTo;

    Date dateFrom = new Date();
    Date dateTo = new Date(AppDateFormat.DATE_AFTER_SEVEN_DAYS);

    int medicineId;
    Medicine medicine;

    @Override
    protected void setView() {
        super.setView();

        if (getIntent().hasExtra("edit_medicine_id")) {
            medicineId = getIntent().getIntExtra("edit_medicine_id", -1);
            medicine = AppDatabaseDAO.selectMedicine(medicineId);
            if(medicine != null) {
                editTitle.setText(medicine.getTitle());
                editDesc.setText(medicine.getDescription());

                textEditDateFrom.setText(
                        AppDateFormat.DATE_FROM.format(medicine.getDateFrom()));
                textEditDateTo.setText(
                        AppDateFormat.DATE_TO.format(medicine.getDateTo()));
            }
        } else {
            medicineId = getIntent().getIntExtra("medicine_id", -1);
            medicine = new Select().from(Medicine.class)
                    .where(Medicine_Table.id.eq(medicineId)).querySingle();
            if(medicine != null) {
                textEditDateFrom.setText(AppDateFormat.DATE_FROM.format(dateFrom));
                textEditDateTo.setText(AppDateFormat.DATE_TO.format(dateTo));
            }
        }

        textEditDateFrom.setOnClickListener(this);
        textEditDateTo.setOnClickListener(this);
    }

    private Date editWhenDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_text_date_from:
                onClickEditDate(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dateFrom = editWhenDate(year, month, day);
                        textEditDateFrom.setText(
                                AppDateFormat.DATE_FROM.format(dateFrom));
                    }
                });
                break;
            case R.id.edit_text_date_to:
                onClickEditDate(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dateTo = editWhenDate(year, month, day);
                        textEditDateFrom.setText(
                                AppDateFormat.DATE_FROM.format(dateTo));
                    }
                });
                break;
        }
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

    private void medicineUpdate() {
        if(editTitle.getText().toString().isEmpty() &&
                editDesc.getText().toString().isEmpty()) {
            AppDatabaseDAO.deleteMedicine(medicineId);
        }
        medicine.setTitle(editTitle.getText().toString());
        medicine.setDescription(editDesc.getText().toString());
        medicine.setDateFrom(dateFrom);
        medicine.setDateTo(dateTo);
        medicine.setModifiedDate(new Date());
        medicine.update();
    }
    @Override
    protected void initView() {
        super.initView();
        editTitle = findViewById(R.id.edit_text_title);
        editDesc = findViewById(R.id.edit_text_desc);
        textEditDateFrom = findViewById(R.id.edit_text_date_from);
        textEditDateTo = findViewById(R.id.edit_text_date_to);
    }

    @Override
    protected String setToolbarTitle() {
        return getIntent().hasExtra("edit_medicine_id") ?
                getString(R.string.main_edit) : getString(R.string.main_add);
    }

    @Override
    protected int setToolbarViewId() {
        return R.id.medicine_edit_toolbar;
    }

    @Override
    protected int setContentViewRes() {
        return R.layout.activity_medicine_edit;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_edit_done) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_toolbar_action, menu);
        // MenuItem item = menu.findItem(R.id.action_setting);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        medicineUpdate();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        medicineUpdate();
        super.onBackPressed();
    }
}
