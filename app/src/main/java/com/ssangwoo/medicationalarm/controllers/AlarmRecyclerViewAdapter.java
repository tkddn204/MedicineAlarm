package com.ssangwoo.medicationalarm.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.List;

/**
 * Created by ssangwoo on 2017-10-29.
 */

public class AlarmRecyclerViewAdapter
        extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {

    private List<Alarm> alarmList;
    private Context context;

    public AlarmRecyclerViewAdapter(Context context, List<Alarm> alarmList) {
        this.context = context;
        this.alarmList = alarmList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Alarm alarm = alarmList.get(position);
        holder.alarmItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 알람 편집
            }
        });

        holder.alarmItemContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle(alarm.getName() + " 삭제")
                        .setMessage(alarm.getName() + "을 삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AppDatabaseDAO.deleteAlarm(alarm.getId());
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        }).create().show();
                return true;
            }
        });

        holder.imageAlarmHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.textAlarmName.setText(alarm.getName());
        holder.textAlarmTime.setText(AppDateFormat.ALARM_TIME.format(alarm.getDate()));
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout alarmItemContainer;
        private ImageView imageAlarmHamburger;
        private TextView textAlarmName, textAlarmTime;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context)
                    .inflate(R.layout.layout_alarm_recycler_item,
                            parent, false));
            alarmItemContainer = itemView.findViewById(R.id.alarm_item_container);
            imageAlarmHamburger = itemView.findViewById(R.id.image_alarm_hamburger);
            textAlarmName = itemView.findViewById(R.id.text_alarm_name);
            textAlarmTime = itemView.findViewById(R.id.text_alarm_time);
        }
    }
}
