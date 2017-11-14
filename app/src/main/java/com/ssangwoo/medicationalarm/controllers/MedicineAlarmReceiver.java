package com.ssangwoo.medicationalarm.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.enums.NotificationActionEnum;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.WhenModel;
import com.ssangwoo.medicationalarm.views.notifications.MedicineKeepNotification;
import com.ssangwoo.medicationalarm.views.notifications.RemoteViewsFactory;

/**
 * Created by ssangwoo on 2017-11-02.
 */

public class MedicineAlarmReceiver extends BroadcastReceiver {
    private static final int MAGIC_NUMBER = 5378;

    public MedicineAlarmReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        new MedicineAlarmController(context).wakeupAlarm();
        MedicineKeepNotification keepAlarmNotification
                = new MedicineKeepNotification(context);

        int id;
        TakeMedicineEnum takeMedicine;

        MedicineModel medicineModel =
                new Select().from(MedicineModel.class).where().querySingle();
        WhenModel whenModel = medicineModel.getWhen();

        if (intent.getAction() != null) {
            String action = intent.getAction();
            if(action.equals(NotificationActionEnum.BREAKFAST_BUTTON_CLICK_ACTION.getAction())) {
                id = NotificationActionEnum.BREAKFAST_BUTTON_CLICK_ACTION.getMappingId();
            } else if(action.equals(NotificationActionEnum.LUNCH_BUTTON_CLICK_ACTION.getAction())) {
                id = NotificationActionEnum.LUNCH_BUTTON_CLICK_ACTION.getMappingId();
            } else if(action.equals(NotificationActionEnum.DINNER_BUTTON_CLICK_ACTION.getAction())) {
                id = NotificationActionEnum.DINNER_BUTTON_CLICK_ACTION.getMappingId();
            }
        }

        RemoteViews remoteViews = new RemoteViewsFactory(context).makeRemoteViews();

        keepAlarmNotification.notify(MAGIC_NUMBER + medicineModel.getId(),
                keepAlarmNotification.makeNotification(remoteViews));
    }
}
