package com.ssangwoo.medicationalarm.controllers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.ssangwoo.medicationalarm.controllers.interfaces.UpdateAlarmRecyclerInterface;
import com.ssangwoo.medicationalarm.controllers.viewHolders.AlarmAddItemViewHolder;
import com.ssangwoo.medicationalarm.controllers.viewHolders.AlarmItemViewHolder;
import com.ssangwoo.medicationalarm.controllers.viewHolders.BindingViewHolder;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;

import java.util.List;

/**
 * Created by ssangwoo on 2017-10-29.
 */

public class AlarmRecyclerViewAdapter
        extends RecyclerView.Adapter<BindingViewHolder>
        implements UpdateAlarmRecyclerInterface {
    private static final int LIST_ADD_ITEM = 10;

    private List<Alarm> alarmList;

    public AlarmRecyclerViewAdapter(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LIST_ADD_ITEM:
                return new AlarmAddItemViewHolder(parent.getContext(), parent,
                        this);
            default:
                return new AlarmItemViewHolder(parent.getContext(), parent,
                        this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(!alarmList.isEmpty() && position == getItemCount() - 1) {
            return LIST_ADD_ITEM;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        if(getItemViewType(position) == LIST_ADD_ITEM) {
            ((AlarmAddItemViewHolder) holder).bindViewHolder(alarmList.get(0).getMedicine());
        } else {
            ((AlarmItemViewHolder) holder).bindViewHolder(alarmList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return alarmList.isEmpty() ? 0 : alarmList.size() + 1;
    }

    @Override
    public void update(int medicineId) {
        this.alarmList = AppDatabaseDAO.selectAlarmList(medicineId);
        notifyDataSetChanged();
    }
}
