package com.ssangwoo.medicationalarm.views.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.AlarmController;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.views.fragments.MedicineRecyclerFragment;

public class MainActivity extends BaseToolbarActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL = 1010;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void setView() {
        super.setView();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_container,
                        MedicineRecyclerFragment.newInstance())
                .commit();

        setFloatingActionButtonVisible();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int requestCode = getResources().getInteger(R.integer.request_edit_medicine);
                Intent intent = new Intent(getApplicationContext(),
                        EditMedicineActivity.class);
                startActivityForResult(intent, requestCode);
            }
        });

        requestPermission();
        new AlarmController(getApplicationContext()).resetAlarm();
    }

    public void setFloatingActionButtonVisible() {
        if(AppDatabaseDAO.selectMedicineList().isEmpty()) {
            floatingActionButton.hide();
        } else {
            floatingActionButton.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setFloatingActionButtonVisible();
    }

    @Override
    protected String setToolbarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected int setToolbarViewId() {
        return R.id.main_toolbar;
    }

    @Override
    protected int setContentViewRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        floatingActionButton = findViewById(R.id.medicine_floating_action_button);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_setting) {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar_action, menu);
        // MenuItem item = menu.findItem(R.id.action_setting);
        return super.onCreateOptionsMenu(menu);
    }

    private void requestPermission() {
        // Activity에서 실행하는경우
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL);
//
//            // 이 권한을 필요한 이유를 설명해야하는가?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//
//            } else {
//
//
//            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }
        }
    }
}
