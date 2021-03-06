package com.ssangwoo.medicationalarm.views.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.controllers.MedicineRecyclerViewAdapter;
import com.ssangwoo.medicationalarm.lib.RecyclerViewEmptySupport;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.views.activities.EditMedicineActivity;
import com.ssangwoo.medicationalarm.views.activities.MainActivity;

import java.util.List;

public class MedicineRecyclerFragment extends Fragment {

    private MedicineRecyclerViewAdapter adapter;
    private RelativeLayout medicineEmptyContainer;

    public MedicineRecyclerFragment() {
        // Required empty public constructor
    }

    public static MedicineRecyclerFragment newInstance() {
        return new MedicineRecyclerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(
                R.layout.fragment_main_medicine_recycler, container, false);

        RecyclerViewEmptySupport medicineRecyclerView =
                view.findViewById(R.id.medicine_recycler_view);
        medicineEmptyContainer = view.findViewById(R.id.medicine_recycler_empty_container);

        medicineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        medicineRecyclerView.setEmptyView(medicineEmptyContainer);
        medicineEmptyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int requestCode = getResources().getInteger(R.integer.request_edit_medicine);
                Intent intent = new Intent(getContext(),
                        EditMedicineActivity.class);
                startActivityForResult(intent, requestCode);
            }
        });

        List<Medicine> medicineList = AppDatabaseDAO.selectMedicineList();
        adapter = new MedicineRecyclerViewAdapter(this, medicineList);
        adapter.setListener(new FlowContentObserver.OnModelStateChangedListener() {
            @Override
            public void onModelStateChanged(@Nullable Class<?> table, BaseModel.Action action,
                                            @NonNull SQLOperator[] primaryKeyValues) {
                adapter.setDataList(AppDatabaseDAO.selectMedicineList());
                adapter.notifyDataSetChanged();
            }
        });
        medicineRecyclerView.setAdapter(adapter);

        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == getResources().getInteger(R.integer.request_show_medicine)) {
//            adapter.notifyDataSetChanged();
//        }
//    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final int medicineId = adapter.getMedicineId();
        Medicine medicine = AppDatabaseDAO.selectMedicine(medicineId);
        if (medicine == null) {
            Toast.makeText(getContext(),
                    getString(R.string.error_loading_medicine), Toast.LENGTH_SHORT).show();
            return false;
        }

        switch (item.getItemId()) {
            case R.id.action_show_edit:
                Intent intent = new Intent(getContext(), EditMedicineActivity.class);
                intent.putExtra("edit_medicine_id", medicineId);
                startActivityForResult(intent, getResources().getInteger(R.integer.request_edit_medicine));
                break;
            case R.id.action_show_delete:
                new AlertDialog.Builder(getContext())
                        .setTitle(medicine.getTitle() + " 삭제")
                        .setMessage(getString(R.string.show_delete_message))
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AppDatabaseDAO.deleteMedicine(medicineId);
                                ((MainActivity)getActivity()).setFloatingActionButtonVisible();
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
