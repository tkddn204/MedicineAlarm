package com.ssangwoo.medicationalarm.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.WhenModel;
import com.ssangwoo.medicationalarm.util.AppDateFormat;
import com.ssangwoo.medicationalarm.views.activities.ShowMedicineActivity;

import java.util.List;

/**
 * Created by ssangwoo on 2017-10-29.
 */

public class MedicineRecyclerViewAdapter
        extends RecyclerView.Adapter<MedicineRecyclerViewAdapter.ViewHolder> {

    List<MedicineModel> models;
    Fragment fragment;
    Context context;

//    public MedicineRecyclerViewAdapter(Context context, List<MedicineModel> models) {
//        this.context = context;
//        this.models = models;
//    }

    public MedicineRecyclerViewAdapter(Fragment fragment, List<MedicineModel> models) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.models = models;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MedicineModel medicineModel = models.get(position);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowMedicineActivity.class);
                intent.putExtra("medicine_id", medicineModel.getId());
                fragment.startActivityForResult(intent,
                        context.getResources().getInteger(R.integer.request_show_medicine));
            }
        });

        holder.textTitle.setText(medicineModel.getTitle());
        holder.textDesc.setText(medicineModel.getDescription());

        holder.textDateFrom.setText(
                AppDateFormat.DATE_FROM.format(medicineModel.getDateFrom()));
        holder.textDateTo.setText(
                AppDateFormat.DATE_TO.format(medicineModel.getDateTo()));

        StringBuilder textWhenBuilder = new StringBuilder();
        WhenModel when = medicineModel.getWhen();
        if(when.isBreakfast()) textWhenBuilder.append("아침\n");
        if(when.isLunch())     textWhenBuilder.append("점심\n");
        if(when.isDinner())    textWhenBuilder.append("저녁\n");
        if(textWhenBuilder.length() != 0) {
            textWhenBuilder.deleteCharAt(textWhenBuilder.length() - 1);
        }

        holder.textWhen.setText(textWhenBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout container;
        private TextView textTitle, textDesc;
        private TextView textDateFrom, textDateTo;
        private TextView textWhen;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context)
                    .inflate(R.layout.layout_medicine_recycler_item,
                            parent, false));
            container = itemView.findViewById(R.id.medicine_recycler_view_item_container);
            textTitle = itemView.findViewById(R.id.medicine_recycler_view_item_title);
            textDesc = itemView.findViewById(R.id.medicine_recycler_view_item_desc);
            textDateFrom = itemView.findViewById(R.id.medicine_recycler_view_item_date_from);
            textDateTo = itemView.findViewById(R.id.medicine_recycler_view_item_date_to);
            textWhen = itemView.findViewById(R.id.medicine_recycler_view_item_when);
        }
    }
}
