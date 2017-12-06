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

    private RemoteViewsFactory(Context context) {
        super(context);
    }

    public static RemoteViewsFactory newInstance(Context context) {
        return new RemoteViewsFactory(context);
    }

    public RemoteViews makeConfirmAlarmRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.layout_confirm_alarm_remote_view);

        Intent onClickIntent = new Intent(this, AlarmReceiver.class);
        for(NotificationActionEnum actionEnum: NotificationActionEnum.values()) {
            if (actionEnum.getMappingId() == 0) {
                continue;
            }
            onClickIntent.setAction(actionEnum.getAction());
            remoteViews.setOnClickPendingIntent(
                    actionEnum.getMappingId(),
                    PendingIntent.getBroadcast(this,
                            getResources().getInteger(R.integer.request_medicine_alarm_broadcast),
                            onClickIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        }
        return remoteViews;
    }
}
