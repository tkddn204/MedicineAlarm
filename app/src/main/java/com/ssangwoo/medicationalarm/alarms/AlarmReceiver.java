package com.ssangwoo.medicationalarm.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.enums.NotificationActionEnum;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AlarmInfo;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.alarms.notifications.AlarmNotification;

import java.util.Calendar;

/**
 * Created by ssangwoo on 2017-11-02.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmController alarmController = new AlarmController(context);
        if(!alarmController.isDeviceScreenOn()) {
            alarmController.wakeupDevice();
        }

        int alarmId = intent.getIntExtra("alarm_id", -1);

        AlarmInfo alarmInfo = AppDatabaseDAO.selectTodayAlarmInfo(alarmId);
        Alarm alarm = alarmInfo.getAlarm();

        AlarmNotification notification = new AlarmNotification(context);

        String action = intent.getAction();
        if (action != null) {
            notification.cancel(alarmInfo.getId()+1);
            if (action.equals(NotificationActionEnum.TAKE_BUTTON_CLICK_ACTION.getAction())) {
                // 복용
//                AppDatabaseDAO.updateTakeMedicine(alarmInfo, TakeMedicineEnum.TAKE);
                AppDatabaseDAO.updateTakeMedicine(alarm, TakeMedicineEnum.TAKE);

                Toast.makeText(context, String.format(
                        context.getString(R.string.take_medicine_toast),
                        alarm.getMedicine().getTitle(), alarm),
                        Toast.LENGTH_SHORT).show();
            } else if (action.equals(NotificationActionEnum.DO_NOT_TAKE_BUTTON_CLICK_ACTION.getAction())) {
                // 미복용
//                AppDatabaseDAO.updateTakeMedicine(alarmInfo, TakeMedicineEnum.DO_NOT_TAKE);
                AppDatabaseDAO.updateTakeMedicine(alarm, TakeMedicineEnum.DO_NOT_TAKE);
                Toast.makeText(context, String.format(
                        context.getString(R.string.do_not_take_medicine_toast),
                        alarm.getMedicine().getTitle()),
                        Toast.LENGTH_SHORT).show();
            } else if (action.equals(NotificationActionEnum.RE_ALARM_BUTTON_CLICK_ACTION.getAction())) {
                // 다시알림
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                int reAlarmMinutes = Integer.parseInt(preferences.getString(
                        context.getString(R.string.pref_key_re_alarm_list), "10"));

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, reAlarmMinutes);

                alarmController.startAlarm(calendar.getTimeInMillis(), alarmId, alarmInfo);
                Toast.makeText(context, String.format(
                        context.getString(R.string.re_alarm_toast), reAlarmMinutes),
                        Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            context.startService(new Intent(context, AlarmSoundService.class));
            notification.makeNotification(context, alarm.getMedicine(), alarmId);
        }
        AppDatabaseDAO.nextAlarmDateUpdate(alarm);
        alarmController.resetAlarm();
    }

}
