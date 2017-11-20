package com.ssangwoo.medicationalarm.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.enums.NotificationActionEnum;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.models.MedicineModel;
import com.ssangwoo.medicationalarm.models.WhenModel;
import com.ssangwoo.medicationalarm.alarms.notifications.AlarmNotification;
import com.ssangwoo.medicationalarm.alarms.notifications.KeepNotification;
import com.ssangwoo.medicationalarm.alarms.notifications.RemoteViewsFactory;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.Calendar;
import java.util.Date;

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
        int medicineId = intent.getIntExtra("medicine_id", -1);
        int alarmId = intent.getIntExtra("alarm_id", -1);
        Medicine medicine = AppDatabaseDAO.selectMedicine(medicineId);
        Alarm alarm = AppDatabaseDAO.selectAlarm(alarmId);

        AlarmNotification alarmNotification
                = new AlarmNotification(context);
        alarmNotification.notify(MAGIC_NUMBER + medicine.getId(),
                alarmNotification.makeNotification(medicine.getTitle()));

        context.startService(new Intent(context, AlarmSoundService.class));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(alarm.getDate());
        calendar.add(Calendar.DATE, 1);
        alarm.setDate(calendar.getTime());
        alarm.update();
        Toast.makeText(context, AppDateFormat.DATE_FROM.format(alarm.getDate()) + " " +
                AppDateFormat.ALARM_TIME.format(alarm.getDate()),
                Toast.LENGTH_SHORT).show();
        alarmController.startAlarm(calendar.getTimeInMillis(), medicineId, alarmId);
    }

    private void keepAlarm(Intent intent) {
        KeepNotification keepAlarmNotification
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
                keepAlarmNotification.makeNotification(remoteViews));
    }
}
