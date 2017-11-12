package com.ssangwoo.medicationalarm.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.datas.SettingData;

/**
 * Created by ssangwoo on 2017-11-12.
 */

public class SettingSwitchViewHolder extends ViewTypeBinder<SettingData> {

    SettingSwitchViewHolder(Context context, ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(context)
                .inflate(layoutId, parent, false));
    }

    void settingBindViewHolder(int position) {
        TextView textSettingName = itemView.findViewById(R.id.text_setting_name);
        Switch switchSetting = itemView.findViewById(R.id.switch_setting);
        //textSettingName.setText(nameList.get(position));
        switchSetting.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    }
                });
    }

    @Override
    void bind(SettingData item) {

    }
}
