package com.ssangwoo.medicationalarm.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.controllers.MedicineRecyclerViewAdapter;
import com.ssangwoo.medicationalarm.models.MedicineModel;

import java.util.List;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(
                R.layout.fragment_main_medicine_list, container, false);

//        TODO: 처음에 디비 감기약 들어가게 하기(디비 비었으면)
//        ArrayList<MedicineModel> medicineModels = new ArrayList<>();
//        MedicineModel medicineModel =
//                new MedicineModel(
//                        "감기약" + Integer.toString(i),
//                        "빨리 낫자" + Integer.toString(i),
//                        new WhenModel(true, false, true)
//                );
//        medicineModel.save();
//        medicineModels.add(medicineModel);
        RecyclerView recyclerView = view.findViewById(R.id.medicine_recycler_view);
        List<MedicineModel> medicineModels =
                new Select().from(MedicineModel.class).queryList();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MedicineRecyclerViewAdapter(getContext(), medicineModels));
        return view;
    }
}
