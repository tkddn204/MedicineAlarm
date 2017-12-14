package com.ssangwoo.medicationalarm.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.notifications.AlarmNotification;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AlarmInfo;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;

import java.util.List;

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

    public void startAlarm(long nextAlarmTime, int alarmId, AlarmInfo alarmInfo) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarm_id", alarmId);
        if (!alarmInfo.getTakeMedicine().equals(TakeMedicineEnum.TAKE)) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    alarmInfo.getPendingRequestNumber(),
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager =
                    (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            assert alarmManager != null;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {;
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                            nextAlarmTime, pendingIntent);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextAlarmTime, pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, nextAlarmTime, pendingIntent);
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
    }

    public void cancelAlarm(int alarmId) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarm_id", alarmId);
        AlarmInfo alarmInfo = AppDatabaseDAO.selectOnlyTodayAlarmInfo(alarmId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                alarmInfo.getPendingRequestNumber(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
        new AlarmNotification(context).cancel(alarmInfo.getPendingRequestNumber());

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            intent = new Intent("android.intent.action.ALARM_CHANGED");
            intent.putExtra("alarmSet", false);
            context.sendBroadcast(intent);
            Settings.System.putString(context.getContentResolver(),
                    Settings.System.NEXT_ALARM_FORMATTED, "");
        }
    }

    public void resetAlarm() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isAlarmOn = preferences.getBoolean(
                context.getString(R.string.pref_key_alarm_switch), true);

        List<Alarm> alarmList = AppDatabaseDAO.selectAllAlarmList();
        if (!alarmList.isEmpty()) {
            for (Alarm alarm : alarmList) {
                if (alarm.isEnable()) {
                    AppDatabaseDAO.nextAlarmDateUpdate(alarm);
                    AlarmInfo alarmInfo = AppDatabaseDAO.selectTodayAlarmInfo(alarm.getId());
                    cancelAlarm(alarmInfo.getAlarm().getId());
                    if (isAlarmOn) {
                        startAlarm(alarm.getDate().getTime(),
                                alarmInfo.getAlarm().getId(), alarmInfo);
                    }
                }
            }
        }
    }

//    public void stopAlarmSoundService() {
//        context.stopService(new Intent(context, AlarmSoundService.class));
//    }

    public void wakeupDevice() {
        PowerManager powerManager =
                (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        assert powerManager != null;
        PowerManager.WakeLock wakeLock =
                powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, MEDICINE_ALARM_RECEIVER);
        wakeLock.acquire(WAKELOCK_ACQUIRE_TIMEOUT);
    }

    public boolean isDeviceScreenOn() {
        PowerManager powerManager =
                (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        assert powerManager != null;
        return powerManager.isScreenOn();
    }
}
