package com.ssangwoo.medicationalarm.alarms.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.AlarmReceiver;
import com.ssangwoo.medicationalarm.enums.NotificationActionEnum;

/**
 * Created by ssangwoo on 2017-11-07.
 */

public class RemoteViewsFactory extends ContextWrapper {


    public RemoteViewsFactory(Context context) {
        super(context);
    }

    public RemoteViews makeRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.layout_keep_alarm_remote_view);

        Intent onClickIntent = new Intent(this, AlarmReceiver.class);
        for(NotificationActionEnum actionEnum: NotificationActionEnum.values()) {
            onClickIntent.setAction(actionEnum.getAction());
            remoteViews.setOnClickPendingIntent(
                    actionEnum.getMappingId(),
                    PendingIntent.getBroadcast(this, 0,
                            onClickIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        }
        return remoteViews;
    }
}
