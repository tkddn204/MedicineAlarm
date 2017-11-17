package com.ssangwoo.medicationalarm.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.controllers.ShowMedicineRecyclerViewAdapter;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.MedicineModel_Table;

import java.util.List;

public class ShowMedicineRecyclerFragment extends Fragment {
    private static final String MEDICINE_ID = "medicine_id";

    MedicineModel medicineModel;

    RecyclerView showRecyclerView;
    ShowMedicineRecyclerViewAdapter adapter;

    OnMedicineObjectListener onMedicineObjectListener;

    public ShowMedicineRecyclerFragment() {
        // Required empty public constructor
    }

    public static ShowMedicineRecyclerFragment newInstance(int medicineId) {
        ShowMedicineRecyclerFragment fragment = new ShowMedicineRecyclerFragment();
        Bundle args = new Bundle();
        args.putInt(MEDICINE_ID, medicineId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
                // TODO : 번들을 못받았을 때
        }

        int medicineId = getArguments().getInt(MEDICINE_ID);

        MedicineModel medicineModel = new Select()
                .from(MedicineModel.class)
                .where(MedicineModel_Table.id.eq(medicineId))
                .querySingle();

        if (medicineModel == null) {
            // TODO: 약 정보가 안나왔을 때
        }

        if(onMedicineObjectListener != null) {
            onMedicineObjectListener.receive(medicineModel);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(
                R.layout.fragment_main_medicine_recycler, container, false);

        showRecyclerView = view.findViewById(R.id.show_recycler_view);
        // TODO: 리스트랑 어댑터
//        List<String> medicineModels =
//        showRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new ShowMedicineRecyclerViewAdapter(this, medicineModels);
        showRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShowMedicineRecyclerFragment.OnMedicineObjectListener) {
            onMedicineObjectListener
                    = (ShowMedicineRecyclerFragment.OnMedicineObjectListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMedicineObjectListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onMedicineObjectListener = null;
    }

    public interface OnMedicineObjectListener {
        void receive(MedicineModel medicineModel);
    }
}
