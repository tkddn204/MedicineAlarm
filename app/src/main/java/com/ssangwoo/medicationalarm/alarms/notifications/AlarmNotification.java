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

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.AlarmReceiver;

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

    public NotificationCompat.Builder makeNotification(String title) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "medicineAlarm");

        String contentText = title + " 드실 시간입니다!";

        return builder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_alarm_small)
                .setContentTitle(title)
                .setContentText(contentText)
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_done_black, getString(R.string.take_medicine),
                PendingIntent.getBroadcast(context, 0,
                        new Intent(this, AlarmReceiver.class), 0))
                .addAction(R.drawable.ic_clear_black, getString(R.string.not_take_medicine),
                        PendingIntent.getBroadcast(context, 0,
                                new Intent(this, AlarmReceiver.class), 0));
    }

    public NotificationCompat.Builder confirmMakeNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "medicineAlarm");

        return builder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_alarm_small)
                .setCustomContentView(new RemoteViews(
                        getPackageName(), R.layout.layout_confirm_alarm_remote_view))
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
    }

    public void notify(int id, NotificationCompat.Builder notification) {
        managerCompat.notify(id, notification.build());
    }

    public void cancel(int id) {
        managerCompat.cancel(id);
    }
}
