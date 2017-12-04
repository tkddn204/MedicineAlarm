package com.ssangwoo.medicationalarm.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.util.AppDateFormat;
import com.ssangwoo.medicationalarm.views.activities.ShowMedicineActivity;

import java.util.List;
import java.util.Locale;

/**
 * Created by ssangwoo on 2017-10-29.
 */

public class MedicineRecyclerViewAdapter
        extends RecyclerView.Adapter<MedicineRecyclerViewAdapter.ViewHolder>
        implements View.OnCreateContextMenuListener {

    List<Medicine> medicineList;
    Fragment fragment;
    Context context;

    public MedicineRecyclerViewAdapter(Fragment fragment, List<Medicine> medicineList) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.medicineList = medicineList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        final int medicineId = medicine.getId();
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowMedicineActivity.class);
                intent.putExtra("medicine_id", medicineId);
                fragment.startActivityForResult(intent,
                        context.getResources().getInteger(R.integer.request_show_medicine));
            }
        });
        holder.container.setOnCreateContextMenuListener(this);
        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setMedicineId(medicineId);
                return false;
            }
        });

        holder.textTitle.setText(medicine.getTitle());
        holder.textDesc.setText(medicine.getDescription());

        String dateString = AppDateFormat.DATE_FROM.format(medicine.getDateFrom())
                + " " + AppDateFormat.DATE_TO.format(medicine.getDateTo());
        holder.textDate.setText(dateString);

        int numberOfAlarm = medicine.getAlarmList().size();
        if(numberOfAlarm > 0) {
            holder.imageAlarm.setImageResource(R.drawable.ic_alarm_black);
            holder.textAlarm.setText(String.format(Locale.KOREA,
                    "알람 %d개", numberOfAlarm));
        }
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout container;
        private TextView textTitle, textDesc, textDate;
        private TextView textAlarm;
        private ImageView imageAlarm;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context)
                    .inflate(R.layout.layout_medicine_recycler_item,
                            parent, false));
            container = itemView.findViewById(R.id.medicine_recycler_view_item_container);
            textTitle = itemView.findViewById(R.id.medicine_recycler_view_item_title);
            textDesc = itemView.findViewById(R.id.medicine_recycler_view_item_desc);
            textDate = itemView.findViewById(R.id.medicine_recycler_view_item_date);
            imageAlarm = itemView.findViewById(R.id.image_medicine_recycler_view_item_alarm);
            textAlarm = itemView.findViewById(R.id.text_medicine_recycler_view_item_alarm);
        }
    }

    private int medicineId;

    public int getMedicineId() {
        return medicineId;
    }

    private void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo) {
        fragment.getActivity().getMenuInflater().inflate(R.menu.menu_show_toolbar_action, contextMenu);
    }
}
