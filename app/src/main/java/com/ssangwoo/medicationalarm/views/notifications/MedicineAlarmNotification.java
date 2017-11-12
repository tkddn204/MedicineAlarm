package com.ssangwoo.medicationalarm.views.notifications;

import android.app.Notification;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import com.ssangwoo.medicationalarm.R;

/**
 * Created by ssangwoo on 2017-11-03.
 */

public class MedicineAlarmNotification extends ContextWrapper {
    Context context;
    NotificationManagerCompat managerCompat;

    public MedicineAlarmNotification(Context context) {
        super(context);
        this.context = context;
        this.managerCompat = NotificationManagerCompat.from(context);
    }

    public NotificationCompat.Builder makeNotification(String title, String when) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "medicineAlarm");

        String contentTitle = title + " " + when;
        String contentText = title + " 드실 시간입니다!";

        return builder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_alarm_small)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSubText("밑으로 슬라이드!")
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
