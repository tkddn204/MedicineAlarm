package com.ssangwoo.medicationalarm.controllers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.views.fragments.MainMedicineAnalysisFragment;
import com.ssangwoo.medicationalarm.views.fragments.MainMedicineCalendarFragment;
import com.ssangwoo.medicationalarm.views.fragments.MainMedicineListFragment;

/**
 * Created by ssangwoo on 2017-10-27.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {
    private static final int FRAGMENT_COUNT = 3;

    private Context context;

    private MainFragmentAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }
    public static MainFragmentAdapter newInstance(
            Context context, FragmentManager fragmentManager) {
        return new MainFragmentAdapter(context, fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MainMedicineListFragment.newInstance();
            case 1:
                return MainMedicineCalendarFragment.newInstance();
            case 2:
                return MainMedicineAnalysisFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}
