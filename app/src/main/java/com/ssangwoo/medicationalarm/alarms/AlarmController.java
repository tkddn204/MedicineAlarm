package com.ssangwoo.medicationalarm.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import com.ssangwoo.medicationalarm.R;

/**
 * Created by ssangwoo on 2017-11-02.
 */

public class AlarmController {

    private static final String MEDICINE_ALARM_RECEIVER = "medicine_receiver";
    private static final long WAKELOCK_ACQUIRE_TIMEOUT = 5000L;
    private final Context context;

    public AlarmController(Context context) {
        this.context = context;
    }

    public void startAlarm(long nextAlarmTime) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                context.getResources().getInteger(R.integer.request_medicine_broadcast),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, nextAlarmTime, pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextAlarmTime, pendingIntent);
            }
            intent = new Intent("android.intent.action.ALARM_CHANGED");
            intent.putExtra("alarm", true);
            context.sendBroadcast(intent);
        } else {
            AlarmManager.AlarmClockInfo alarmClockInfo
                    = new AlarmManager.AlarmClockInfo(nextAlarmTime, pendingIntent);
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
        }
    }

    public void cancelAlarm() {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            intent = new Intent("android.intent.action.ALARM_CHANGED");
            intent.putExtra("alarmSet", false);
            context.sendBroadcast(intent);
            Settings.System.putString(context.getContentResolver(),
                    Settings.System.NEXT_ALARM_FORMATTED, "");
        }
    }

    public void wakeupAlarm() {
        PowerManager powerManager =
                (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock =
                powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, MEDICINE_ALARM_RECEIVER);
        wakeLock.acquire(WAKELOCK_ACQUIRE_TIMEOUT);
    }

    public void stopAlarm() {
        context.stopService(new Intent(context, AlarmService.class));
    }

}
