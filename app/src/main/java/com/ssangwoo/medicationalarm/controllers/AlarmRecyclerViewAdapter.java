package com.ssangwoo.medicationalarm.controllers;

import android.view.ViewGroup;

import com.ssangwoo.medicationalarm.controllers.viewHolders.AlarmAddItemViewHolder;
import com.ssangwoo.medicationalarm.controllers.viewHolders.AlarmItemViewHolder;
import com.ssangwoo.medicationalarm.controllers.viewHolders.BindingViewHolder;
import com.ssangwoo.medicationalarm.lib.ObservableAdapter;
import com.ssangwoo.medicationalarm.models.Alarm;

import java.util.List;

/**
 * Created by ssangwoo on 2017-10-29.
 */

public class AlarmRecyclerViewAdapter extends ObservableAdapter<BindingViewHolder> {
    private static final int LIST_ADD_ITEM = 10;

    public AlarmRecyclerViewAdapter(List<Alarm> alarmList) {
        super(Alarm.class);
        this.dataList = alarmList;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LIST_ADD_ITEM:
                return new AlarmAddItemViewHolder(parent.getContext(), parent);
            default:
                return new AlarmItemViewHolder(parent.getContext(), parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(!dataList.isEmpty() && position == getItemCount() - 1) {
            return LIST_ADD_ITEM;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        if(getItemViewType(position) == LIST_ADD_ITEM) {
            ((AlarmAddItemViewHolder) holder).bindViewHolder(
                    ((Alarm)dataList.get(0)).getMedicine());
        } else {
            ((AlarmItemViewHolder) holder).bindViewHolder(
                    (Alarm)dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.isEmpty() ? 0 : dataList.size() + 1;
    }

}
