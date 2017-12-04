package com.ssangwoo.medicationalarm.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.enums.NotificationActionEnum;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.alarms.notifications.AlarmNotification;
import com.ssangwoo.medicationalarm.alarms.notifications.KeepNotification;
import com.ssangwoo.medicationalarm.alarms.notifications.RemoteViewsFactory;

import java.util.Calendar;

/**
 * Created by ssangwoo on 2017-11-02.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final int MAGIC_NUMBER = 5378;

    public AlarmReceiver() {
        super();
    }

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        AlarmController alarmController = new AlarmController(context);
        alarmController.wakeupAlarm();
        //keepAlarm(intent);
        int alarmId = intent.getIntExtra("alarm_id", -1);
        Alarm alarm = AppDatabaseDAO.selectAlarm(alarmId);
        Medicine medicine = alarm.getMedicine();

        AlarmNotification alarmNotification
                = new AlarmNotification(context);
        alarmNotification.notify(MAGIC_NUMBER + medicine.getId(),
                alarmNotification.makeNotification(medicine.getTitle()));

        context.startService(new Intent(context, AlarmSoundService.class));

        AppDatabaseDAO.nextAlarmUpdate(alarmId);
        alarmController.startAlarm(alarm.getDate().getTime(), alarmId);
    }

    private void keepAlarm(Intent intent) {
        /*KeepNotification keepAlarmNotification
                = new KeepNotification(context);

        int id;
        TakeMedicineEnum takeMedicine;

        MedicineModel medicineModel =
                new Select().from(MedicineModel.class).where().querySingle();

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
                keepAlarmNotification.makeNotification(remoteViews));*/
    }
}
