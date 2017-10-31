package com.ssangwoo.medicationalarm.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssangwoo.medicationalarm.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMedicineCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMedicineCalendarFragment extends Fragment {

    public MainMedicineCalendarFragment() {
        // Required empty public constructor
    }

    public static MainMedicineCalendarFragment newInstance() {
        MainMedicineCalendarFragment fragment = new MainMedicineCalendarFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_medicine_calendar, container, false);
    }

}
