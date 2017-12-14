package com.ssangwoo.medicationalarm.views.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.Calendar;
import java.util.Date;

public class EditMedicineActivity extends BaseToolbarWithBackButtonActivity
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditText editTitle, editDesc;
    private TextView textEditDateFrom, textEditDateTo;

    private Medicine medicine;

    @Override
    protected void setView() {
        super.setView();

        if (getIntent().hasExtra("edit_medicine_id")) {
            int medicineId = getIntent().getIntExtra("edit_medicine_id", -1);
            medicine = AppDatabaseDAO.selectMedicine(medicineId);
            if (medicine != null) {
                editTitle.setText(medicine.getTitle());
                editDesc.setText(medicine.getDescription());

                textEditDateFrom.setText(
                        AppDateFormat.DATE_YEAR_FROM.format(medicine.getDateFrom()));
                textEditDateTo.setText(
                        AppDateFormat.DATE_YEAR_TO.format(medicine.getDateTo()));
            }
        } else {
            medicine = AppDatabaseDAO.createMedicine();
            if (medicine != null) {
                textEditDateFrom.setText(AppDateFormat.DATE_YEAR_FROM.format(medicine.getDateFrom()));
                textEditDateTo.setText(AppDateFormat.DATE_YEAR_TO.format(medicine.getDateTo()));
            }
        }

        textEditDateFrom.setOnClickListener(this);
        textEditDateTo.setOnClickListener(this);
    }

    private Date editWhenDate(int year, int month, int day, boolean isFrom) {
        Calendar calendar = Calendar.getInstance();
        if (isFrom) {
            calendar.set(year, month, day, 0, 0, 0);
        } else {
            calendar.set(year, month, day, 23, 59, 59);
        }
        return calendar.getTime();
    }


    private boolean isFrom;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_text_date_from:
                isFrom = true;
                onClickEditDate(medicine.getDateFrom());
                break;
            case R.id.edit_text_date_to:
                isFrom = false;
                onClickEditDate(medicine.getDateTo());
                break;
        }
    }

    private void onClickEditDate(Date editDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(editDate);
        Context context;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            context = new ContextThemeWrapper(
                    this, android.R.style.Theme_Holo_Light_Dialog);
        } else {
            context = this;
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Date pickDate = editWhenDate(year, month, day, isFrom);
        if(isFrom) {
            if (medicine.getDateTo().before(pickDate)) {
                makeAlertDialog(getString(R.string.error_date_from));
                return;
            }
            medicine.setDateFrom(pickDate);
            textEditDateFrom.setText(
                    AppDateFormat.DATE_YEAR_FROM.format(pickDate));
        } else {
            if (medicine.getDateFrom().after(pickDate)) {
                makeAlertDialog(getString(R.string.error_date_to));
                return;
            }
            medicine.setDateTo(pickDate);
            textEditDateTo.setText(
                    AppDateFormat.DATE_YEAR_TO.format(pickDate));
        }

    }

    private void makeAlertDialog(String message) {
        new AlertDialog.Builder(EditMedicineActivity.this)
                .setMessage(message)
                .setPositiveButton(getText(R.string.close_dialog),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(isFrom) {
                                    onClickEditDate(medicine.getDateFrom());
                                } else {
                                    onClickEditDate(medicine.getDateTo());
                                }
                                dialogInterface.cancel();
                            }
                        })
                .create().show();
    }

    private void medicineUpdate() {
        if (editTitle.getText().toString().isEmpty() &&
                editDesc.getText().toString().isEmpty()) {
            AppDatabaseDAO.deleteMedicine(medicine.getId());
        } else {
            AppDatabaseDAO.updateMedicine(medicine,
                    editTitle.getText().toString(),
                    editDesc.getText().toString());
            setResult(RESULT_OK);
        }
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
        if (item.getItemId() == R.id.action_edit_done) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_toolbar_action, menu);
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
