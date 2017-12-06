package com.ssangwoo.medicationalarm.alarms.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import com.ssangwoo.medicationalarm.App;
import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.AlarmReceiver;
import com.ssangwoo.medicationalarm.enums.NotificationActionEnum;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AlarmInfo;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;

import java.util.Date;

/**
 * Created by ssangwoo on 2017-11-03.
 */

public class AlarmNotification extends ContextWrapper {
    Context context;
    NotificationManagerCompat managerCompat;

    public AlarmNotification(Context context) {
        super(context);
        this.context = context;
        this.managerCompat = NotificationManagerCompat.from(context);
    }

    public NotificationCompat.Builder makeNotification(String title, int medicineId, int alarmId) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "medicineAlarm");

        Intent confirmIntent = new Intent(this, AlarmReceiver.class);
        confirmIntent.putExtra("alarm_id", alarmId);

        Intent takeIntent = new Intent(this, AlarmReceiver.class);
        takeIntent.setAction(NotificationActionEnum.TAKE_BUTTON_CLICK_ACTION.getAction());
        takeIntent.putExtra("alarm_id", alarmId);

        Intent doNotTakeIntent = new Intent(this, AlarmReceiver.class);
        doNotTakeIntent.setAction(NotificationActionEnum.DO_NOT_TAKE_BUTTON_CLICK_ACTION.getAction());
        doNotTakeIntent.putExtra("alarm_id", alarmId);

        Intent reAlarmIntent = new Intent(this, AlarmReceiver.class);
        reAlarmIntent.setAction(NotificationActionEnum.RE_ALARM_BUTTON_CLICK_ACTION.getAction());
        reAlarmIntent.putExtra("alarm_id", alarmId);

        String contentText = title + " 드실 시간입니다!";

        return builder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_alarm_small)
                .setContentTitle(title)
                .setContentText(contentText)
                .setContentIntent(PendingIntent.getBroadcast(context,
                        getResources().getInteger(R.integer.request_medicine_alarm_broadcast) * (medicineId+1) * (alarmId+1),
                        confirmIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_done_black, getString(R.string.notification_take_medicine),
                        PendingIntent.getBroadcast(context,
                                getResources().getInteger(R.integer.request_medicine_alarm_broadcast) * (medicineId+1) * (alarmId+1),
                                takeIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_clear_black, getString(R.string.notification_not_take_medicine),
                        PendingIntent.getBroadcast(context,
                                getResources().getInteger(R.integer.request_medicine_alarm_broadcast) * (medicineId+1) * (alarmId+1),
                                doNotTakeIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_alarm_black, getString(R.string.notification_re_alarm),
                        PendingIntent.getBroadcast(context,
                                getResources().getInteger(R.integer.request_medicine_alarm_broadcast) * (medicineId+1) * (alarmId+1),
                                reAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public NotificationCompat.Builder confirmMakeNotification(RemoteViews remoteViews) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "medicineAlarm");

        return builder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_alarm_small)
                .setCustomContentView(remoteViews)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
    }

    public void notify(int id, NotificationCompat.Builder notification) {
        managerCompat.notify(id, notification.build());
    }

    public void cancel(int id) {
        managerCompat.cancel(id);
    }
}
