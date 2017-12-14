package com.ssangwoo.medicationalarm.controllers.viewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.views.dialogs.EditAlarmTimeDialog;

/**
 * Created by ssangwoo on 2017-12-04.
 */

public class AlarmAddItemViewHolder extends BindingViewHolder<Medicine> {
    private Context context;
    private TextView textAlarmLimit;
    private ImageView imageAlarmLimit;

    public AlarmAddItemViewHolder(Context context, ViewGroup parent) {
        super(LayoutInflater.from(context)
                .inflate(R.layout.layout_alarm_add_recycler_item,
                        parent, false));
        this.context = context;

        textAlarmLimit = itemView.findViewById(R.id.text_alarm_limit);
        imageAlarmLimit = itemView.findViewById(R.id.image_alarm_add);
    }

    @Override
    public void bindViewHolder(final Medicine medicine) {
        if(medicine.getAlarmList().size() < 5) {
            textAlarmLimit.setVisibility(View.GONE);
            imageAlarmLimit.setVisibility(View.VISIBLE);
            imageAlarmLimit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new EditAlarmTimeDialog(context, medicine).make();
                }
            });
        } else {
            textAlarmLimit.setVisibility(View.VISIBLE);
            imageAlarmLimit.setVisibility(View.GONE);
        }
    }
}
