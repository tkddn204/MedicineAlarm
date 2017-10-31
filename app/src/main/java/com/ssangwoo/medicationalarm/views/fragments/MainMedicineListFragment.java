package com.ssangwoo.medicationalarm.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.controllers.MedicineRecyclerViewAdapter;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.WhenModel;
import com.ssangwoo.medicationalarm.views.activities.EditMedicineActivity;

import java.util.ArrayList;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class MainMedicineListFragment extends Fragment {

    public MainMedicineListFragment() {
        // Required empty public constructor
    }
    public static MainMedicineListFragment newInstance() {
        MainMedicineListFragment fragment = new MainMedicineListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(
                R.layout.fragment_main_medicine_list, container, false);

        // ----dummy----
        ArrayList<MedicineModel> medicineModels = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            MedicineModel medicineModel =
                    new MedicineModel(
                            "테스트타이틀" + Integer.toString(i),
                            "테스트설명" + Integer.toString(i),
                            new WhenModel(random.nextBoolean(),random.nextBoolean(),random.nextBoolean())
                    );
            medicineModel.save();
            medicineModels.add(medicineModel);
        }
        // -------------

        initView(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MedicineRecyclerViewAdapter(medicineModels));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getContext(), EditMedicineActivity.class),
                        getResources().getInteger(R.integer.request_edit_medicine));
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 메인액티비티에 액티비티리줄트 만들기(리스트, 일정, 분석 리프레쉬)
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.medicine_recycler_view);
        floatingActionButton = view.findViewById(R.id.medicine_floating_action_button);
    }

}
