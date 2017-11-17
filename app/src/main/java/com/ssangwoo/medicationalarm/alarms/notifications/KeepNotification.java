package com.ssangwoo.medicationalarm.alarms.notifications;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

import com.ssangwoo.medicationalarm.R;

/**
 * Created by ssangwoo on 2017-11-03.
 */

public class KeepNotification extends ContextWrapper {
    Context context;
    NotificationManagerCompat managerCompat;

    public KeepNotification(Context context) {
        super(context);
        this.context = context;
        this.managerCompat = NotificationManagerCompat.from(context);
    }

    public NotificationCompat.Builder makeNotification(RemoteViews remoteView) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "medicineAlarm");
        return builder.setCustomContentView(remoteView)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_plus);
    }

    public void notify(int id, NotificationCompat.Builder notification) {
        managerCompat.notify(id, notification.build());
    }

    public void cancel(int id) {
        managerCompat.cancel(id);
    }
}
