package com.ssangwoo.medicationalarm.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssangwoo.medicationalarm.R;

@Deprecated
public class MedicineCalendarFragment extends Fragment {

    public MedicineCalendarFragment() {
        // Required empty public constructor
    }

    public static MedicineCalendarFragment newInstance() {
        MedicineCalendarFragment fragment = new MedicineCalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_medicine_calendar, container, false);
    }

}
