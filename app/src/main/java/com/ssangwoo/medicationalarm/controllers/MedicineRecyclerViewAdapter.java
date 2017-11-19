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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.WhenModel;
import com.ssangwoo.medicationalarm.util.AppDateFormat;
import com.ssangwoo.medicationalarm.views.activities.ShowMedicineActivityListener;

import java.util.List;

/**
 * Created by ssangwoo on 2017-10-29.
 */

public class MedicineRecyclerViewAdapter
        extends RecyclerView.Adapter<MedicineRecyclerViewAdapter.ViewHolder>
        implements View.OnCreateContextMenuListener {

    List<MedicineModel> medicines;
    Fragment fragment;
    Context context;

    public MedicineRecyclerViewAdapter(Fragment fragment, List<MedicineModel> medicines) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.medicines = medicines;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MedicineModel medicineModel = medicines.get(position);
        WhenModel whenModel = medicineModel.getWhen();
        final int medicineId = medicineModel.getId();
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowMedicineActivityListener.class);
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

        holder.textTitle.setText(medicineModel.getTitle());
        holder.textDesc.setText(medicineModel.getDescription());

        holder.textDateFrom.setText(
                AppDateFormat.DATE_FROM.format(medicineModel.getDateFrom()));
        holder.textDateTo.setText(
                AppDateFormat.DATE_TO.format(medicineModel.getDateTo()));

//        if(whenModel.isBreakfast()) {
//            holder.whenBreakfastContainer.setVisibility(View.VISIBLE);
//            if(whenModel.isBreakfastAlarm()) {
//                holder.whenBreakfastAlarmImage.setImageResource(R.drawable.ic_notifications_black);
//            }
//        }
//        if(whenModel.isLunch()) {
//            holder.whenLunchContainer.setVisibility(View.VISIBLE);
//            if(whenModel.isLunchAlarm()) {
//                holder.whenLunchAlarmImage.setImageResource(R.drawable.ic_notifications_black);
//            }
//        }
//        if(whenModel.isDinner()) {
//            holder.whenDinnerContainer.setVisibility(View.VISIBLE);
//            if(whenModel.isDinnerAlarm()) {
//                holder.whenDinnerAlarmImage.setImageResource(R.drawable.ic_notifications_black);
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout container;
        private TextView textTitle, textDesc;
        private TextView textDateFrom, textDateTo;
        private LinearLayout whenBreakfastContainer, whenLunchContainer, whenDinnerContainer;
        private ImageView whenBreakfastAlarmImage, whenLunchAlarmImage, whenDinnerAlarmImage;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context)
                    .inflate(R.layout.layout_medicine_recycler_item,
                            parent, false));
            container = itemView.findViewById(R.id.medicine_recycler_view_item_container);
            textTitle = itemView.findViewById(R.id.medicine_recycler_view_item_title);
            textDesc = itemView.findViewById(R.id.medicine_recycler_view_item_desc);
            textDateFrom = itemView.findViewById(R.id.medicine_recycler_view_item_date_from);
            textDateTo = itemView.findViewById(R.id.medicine_recycler_view_item_date_to);

//            whenBreakfastContainer
//                    = itemView.findViewById(R.id.medicine_recycler_view_item_when_breakfast);
//            whenLunchContainer
//                    = itemView.findViewById(R.id.medicine_recycler_view_item_when_lunch);
//            whenDinnerContainer
//                    = itemView.findViewById(R.id.medicine_recycler_view_item_when_dinner);
//
//            whenBreakfastAlarmImage
//                    = itemView.findViewById(R.id.medicine_recycler_view_item_when_breakfast_alarm);
//            whenLunchAlarmImage
//                    = itemView.findViewById(R.id.medicine_recycler_view_item_when_lunch_alarm);
//            whenDinnerAlarmImage
//                    = itemView.findViewById(R.id.medicine_recycler_view_item_when_dinner_alarm);
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
