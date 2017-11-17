package com.ssangwoo.medicationalarm.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.MedicineModel_Table;
import com.ssangwoo.medicationalarm.views.fragments.ShowMedicineRecyclerFragment;

public class ShowMedicineActivityListener extends BaseToolbarWithBackButtonActivity
        implements ShowMedicineRecyclerFragment.OnMedicineObjectListener {

    ShowMedicineRecyclerFragment showFragment;

    MedicineModel medicineModel;

    @Override
    protected void setView() {
        super.setView();
        int medicineId = getIntent().getIntExtra("medicine_id", 0);

        showFragment = ShowMedicineRecyclerFragment.newInstance(medicineId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.show_fragment_container, showFragment)
                .commit();
    }

    @Override
    public void receive(MedicineModel medicineModel) {
        this.medicineModel = medicineModel;
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
