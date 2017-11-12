//package com.ssangwoo.medicationalarm.controllers;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.ssangwoo.medicationalarm.R;
//import com.ssangwoo.medicationalarm.enums.SettingTypeEnum;
//
///**
// * Created by ssangwoo on 2017-11-12.
// */
//
//public class SettingViewHolderFactory {
//    public static int getItemLayoutId(int type) {
//        int resLayoutId = 0;
//        if(type == SettingTypeEnum.SWITCH.getValue()) {
//            resLayoutId = R.layout.layout_setting_recycler_item;
//        } else if (type == SettingTypeEnum.SELECT.getValue()) {
//            resLayoutId = R.layout.layout_setting_recycler_item;
//        }
//        return resLayoutId;
//    }
//
//    public static RecyclerView.ViewHolder getViewHolder(Context context, int type, View itemView) {
//        RecyclerView.ViewHolder viewHolder;
//        if(type == SettingTypeEnum.SWITCH.getValue()) {
//            viewHolder = new SettingSwitchViewHolder();
//        } else if (type == SettingTypeEnum.SELECT.getValue()) {
//            viewHolder = new SettingSelectVie0wHolder();
//        }
//
//    }
//}
