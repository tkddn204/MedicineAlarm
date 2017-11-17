package com.ssangwoo.medicationalarm.views.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.FlowCursor;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.MedicineModel_Table;
import com.ssangwoo.medicationalarm.views.fragments.MainMedicineListFragment;

public class MainActivity extends BaseActivity {

    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;

    MainMedicineListFragment mainFragment;

    @Override
    protected void setView() {
        collapsingToolbarLayout.setTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        mainFragment = MainMedicineListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment, mainFragment)
                .commit();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0) {
                    floatingActionButton.show();
                } else {
                    floatingActionButton.hide();
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int requestCode = getResources().getInteger(R.integer.request_edit_medicine);
                Intent intent = new Intent(getApplicationContext(),
                        EditMedicineActivity.class);
                MedicineModel medicineModel = new MedicineModel();
                medicineModel.insert();
                intent.putExtra("medicine_id", medicineModel.getId());
                mainFragment.startActivityForResult(intent, requestCode);
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        appBarLayout = findViewById(R.id.main_app_bar_layout);
        collapsingToolbarLayout = findViewById(R.id.main_collapsing_toolbar_layout);
        toolbar = findViewById(R.id.main_toolbar);
        floatingActionButton = findViewById(R.id.medicine_floating_action_button);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_setting) {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar_action, menu);
        // MenuItem item = menu.findItem(R.id.action_setting);
        return super.onCreateOptionsMenu(menu);
    }
}
