package com.ssangwoo.medicationalarm.controllers;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.datas.SettingData;

import java.util.List;

/**
 * Created by ssangwoo on 2017-11-11.
 */

public class SettingRecyclerViewAdapter
        extends RecyclerView.Adapter<SettingSwitchViewHolder> {

    private Context context;
    private List<String> nameList;

    public SettingRecyclerViewAdapter(Context context, List<String> nameList) {
        this.context = context;
        this.nameList = nameList;
    }

    @Override
    public SettingSwitchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        switch (viewType) {
            default:
                layoutId = R.layout.layout_setting_recycler_item;
                break;
        }
        return new SettingSwitchViewHolder(parent.getContext(), parent, layoutId);
    }

    @Override
    public void onBindViewHolder(SettingSwitchViewHolder holder, int position) {
        //holder.SettingSwitchViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }
}
