package com.ssangwoo.medicationalarm.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ssangwoo on 2017-11-20.
 */

public class AlarmBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction() == null ? "" : intent.getAction();
        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            new AlarmController(context).resetAlarm();
        }
    }
}
