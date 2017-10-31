package com.ssangwoo.medicationalarm.controllers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.WhenModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ssangwoo on 2017-10-29.
 */

public class MedicineRecyclerViewAdapter
        extends RecyclerView.Adapter<MedicineRecyclerViewAdapter.ViewHolder> {

    ArrayList<MedicineModel> models;

    public MedicineRecyclerViewAdapter(ArrayList<MedicineModel> models) {
        this.models = models;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MedicineModel medicineModel = models.get(position);

        holder.textTitle.setText(medicineModel.getTitle());
        holder.textDesc.setText(medicineModel.getDescription());

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy년 MM월 dd일부터", Locale.KOREA);
        holder.textDateFrom.setText(dateFormat.format(medicineModel.getDateFrom()));
        dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일까지", Locale.KOREA);
        holder.textDateTo.setText(dateFormat.format(medicineModel.getDateTo()));

        StringBuilder textWhenBuilder = new StringBuilder();
        WhenModel when = medicineModel.getWhen();
        if(when.isMorning()) {
            textWhenBuilder.append("아침\n");
        }
        if(when.isAfternoon()) {
            textWhenBuilder.append("점심\n");
        }
        if(when.isDinner()) {
            textWhenBuilder.append("저녁\n");
        }
        if(textWhenBuilder.length() != 0) {
            textWhenBuilder.deleteCharAt(textWhenBuilder.length() - 1);
        }
        holder.textWhen.setText(textWhenBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle, textDesc;
        private TextView textDateFrom, textDateTo;
        private TextView textWhen;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context)
                    .inflate(R.layout.layout_medicine_recycler_item,
                            parent, false));
            textTitle = itemView.findViewById(R.id.medicine_recycler_view_item_title);
            textDesc = itemView.findViewById(R.id.medicine_recycler_view_item_desc);
            textDateFrom = itemView.findViewById(R.id.medicine_recycler_view_item_date_from);
            textDateTo = itemView.findViewById(R.id.medicine_recycler_view_item_date_to);
            textWhen = itemView.findViewById(R.id.medicine_recycler_view_item_when);
        }


    }
}
