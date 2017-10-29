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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainMedicineCalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
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
