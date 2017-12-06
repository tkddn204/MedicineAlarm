package com.ssangwoo.medicationalarm.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.enums.NotificationActionEnum;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.alarms.notifications.AlarmNotification;
import com.ssangwoo.medicationalarm.alarms.notifications.RemoteViewsFactory;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.Calendar;

/**
 * Created by ssangwoo on 2017-11-02.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {
        super();
    }

    private Context context;

    private int alarmId;
    private Medicine medicine;
    private AlarmNotification alarmNotification;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        AlarmController alarmController = new AlarmController(context);
        alarmController.wakeupAlarm();

        alarmId = intent.getIntExtra("alarm_id", -1);
        Alarm alarm = AppDatabaseDAO.selectAlarm(alarmId);
        medicine = alarm.getMedicine();
        alarmNotification = new AlarmNotification(context);

        String action = intent.getAction();
        if (action != null) {
            if (action.equals(NotificationActionEnum.TAKE_BUTTON_CLICK_ACTION.getAction())) {
                // 복용
                AppDatabaseDAO.updateTakeMedicine(
                        AppDatabaseDAO.selectTodayAlarmInfo(alarmId), TakeMedicineEnum.TAKE);
                alarmNotification.cancel(
                        context.getResources().getInteger(R.integer.request_medicine_alarm_broadcast)
                                * (medicine.getId()+1) * (alarmId+1));
            } else if (action.equals(NotificationActionEnum.DO_NOT_TAKE_BUTTON_CLICK_ACTION.getAction())) {
                // 미복용
                AppDatabaseDAO.updateTakeMedicine(
                        AppDatabaseDAO.selectTodayAlarmInfo(alarmId), TakeMedicineEnum.DO_NOT_TAKE);
                alarmNotification.cancel(
                        context.getResources().getInteger(R.integer.request_medicine_alarm_broadcast)
                                * (medicine.getId()+1) * (alarmId+1));
            } else if (action.equals(NotificationActionEnum.RE_ALARM_BUTTON_CLICK_ACTION.getAction())) {
                // 다시알림
                // TODO : 다시알림 설정에 들어가서 바꾸는거
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, 10);
                alarmController.startAlarm(calendar.getTimeInMillis(), medicine.getId(), alarmId);
                Toast.makeText(context, String.format(
                        context.getString(R.string.re_alarm_toast), 10), Toast.LENGTH_SHORT).show();
                alarmNotification.cancel(
                        context.getResources().getInteger(R.integer.request_medicine_alarm_broadcast)
                                * (medicine.getId()+1) * (alarmId+1));
                return;
            }
        } else {
            context.startService(new Intent(context, AlarmSoundService.class));
            firstNotificationAlarm();
        }
        AppDatabaseDAO.nextAlarmDateUpdate(alarm);
        alarmController.resetAlarm();
        //alarmController.startAlarm(alarm.getDate().getTime(), alarmId);
    }

    private void firstNotificationAlarm() {
        alarmNotification.notify(
                context.getResources().getInteger(R.integer.request_medicine_alarm_broadcast)
                        * (medicine.getId()+1) * (alarmId+1),
                alarmNotification.makeNotification(medicine.getTitle(), medicine.getId(), alarmId));
    }
}
