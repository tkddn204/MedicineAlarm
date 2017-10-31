package com.ssangwoo.medicationalarm.views.activities;

import android.widget.TextView;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.MedicineModel_Table;
import com.ssangwoo.medicationalarm.models.WhenModel;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

public class ShowMedicineActivity extends BaseToolbarWithBackButtonActivity {

    TextView showDesc;
    TextView showDateFrom, showDateTo;
    TextView showMorning, showAfternoon, showDinner;

    MedicineModel medicineModel;

    @Override
    protected void setView() {
        super.setView();

        WhenModel when = medicineModel.getWhen();

        showDesc.setText(medicineModel.getDescription());
        showDateFrom.setText(AppDateFormat.DATE_FROM.format(medicineModel.getDateFrom()));
        showDateTo.setText(AppDateFormat.DATE_TO.format(medicineModel.getDateTo()));
        showMorning.setText(Boolean.toString(when.isMorning()));
        showAfternoon.setText(Boolean.toString(when.isAfternoon()));
        showDinner.setText(Boolean.toString(when.isDinner()));
    }

    @Override
    protected void initView() {
        super.initView();
        int medicineId = getIntent().getIntExtra("medicine_id", 0);
        medicineModel = new Select()
                .from(MedicineModel.class)
                .where(MedicineModel_Table.id.eq(medicineId))
                .querySingle();

        showDesc = findViewById(R.id.text_show_desc);
        showDateFrom = findViewById(R.id.text_show_date_from);
        showDateTo = findViewById(R.id.text_show_date_to);
        showMorning = findViewById(R.id.text_show_morning);
        showAfternoon = findViewById(R.id.text_show_afternoon);
        showDinner = findViewById(R.id.text_show_dinner);
    }

    @Override
    protected String setToolbarTitle() {
        return medicineModel.getTitle();
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
