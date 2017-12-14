package com.ssangwoo.medicationalarm.alarms.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.AlarmReceiver;
import com.ssangwoo.medicationalarm.enums.NotificationActionEnum;
import com.ssangwoo.medicationalarm.models.AlarmInfo;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.views.activities.ShowMedicineActivity;

/**
 * Created by ssangwoo on 2017-11-03.
 */

public class AlarmNotification extends ContextWrapper {
    NotificationManagerCompat notificationManager;

    public AlarmNotification(Context context) {
        super(context);
        this.notificationManager = NotificationManagerCompat.from(context);
    }

    public void makeNotification(Context context, Medicine medicine, int alarmId) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "medicineAlarm");

        AlarmInfo alarmInfo = AppDatabaseDAO.selectTodayAlarmInfo(alarmId);

        Intent showMedicineIntent = new Intent(this, ShowMedicineActivity.class);
        showMedicineIntent.putExtra("medicine_id", medicine.getId());
        showMedicineIntent.putExtra("notification_id", alarmInfo.getId()+1);

        Intent takeIntent = new Intent(this, AlarmReceiver.class);
        takeIntent.setAction(NotificationActionEnum.TAKE_BUTTON_CLICK_ACTION.getAction());
        takeIntent.putExtra("alarm_id", alarmId);

        Intent doNotTakeIntent = new Intent(this, AlarmReceiver.class);
        doNotTakeIntent.setAction(NotificationActionEnum.DO_NOT_TAKE_BUTTON_CLICK_ACTION.getAction());
        doNotTakeIntent.putExtra("alarm_id", alarmId);

        Intent reAlarmIntent = new Intent(this, AlarmReceiver.class);
        reAlarmIntent.setAction(NotificationActionEnum.RE_ALARM_BUTTON_CLICK_ACTION.getAction());
        reAlarmIntent.putExtra("alarm_id", alarmId);

        String title = medicine.getTitle() + " 드실 시간입니다!";

        builder.setColor(ContextCompat.getColor(context, R.color.colorPrimary))
               .setSmallIcon(R.drawable.ic_alarm_small)
               .setContentTitle(title)
               .setContentText(medicine.getDescription())
               .setContentIntent(PendingIntent.getActivity(context,
                       alarmInfo.getPendingRequestNumber(),
                       showMedicineIntent, PendingIntent.FLAG_CANCEL_CURRENT))
               .setDefaults(Notification.DEFAULT_ALL)
               .setPriority(NotificationCompat.PRIORITY_HIGH)
               .addAction(R.drawable.ic_done_black, getString(R.string.notification_take_medicine),
                       PendingIntent.getBroadcast(context,
                               alarmInfo.getPendingRequestNumber(),
                               takeIntent, PendingIntent.FLAG_CANCEL_CURRENT))
               .addAction(R.drawable.ic_clear_black, getString(R.string.notification_not_take_medicine),
                       PendingIntent.getBroadcast(context,
                               alarmInfo.getPendingRequestNumber(),
                               doNotTakeIntent, PendingIntent.FLAG_CANCEL_CURRENT))
               .addAction(R.drawable.ic_alarm_black, getString(R.string.notification_re_alarm),
                       PendingIntent.getBroadcast(context,
                               alarmInfo.getPendingRequestNumber(),
                               reAlarmIntent, PendingIntent.FLAG_CANCEL_CURRENT));

        NotificationManagerCompat.from(context).
                notify(alarmInfo.getId()+1, builder.build());
    }

    public void cancel(int id) {
        notificationManager.cancel(id);
    }
}
