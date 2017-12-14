package com.ssangwoo.medicationalarm.views.dialogs;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.AlarmController;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AlarmInfo;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;
import com.ssangwoo.medicationalarm.util.AppDateFormat;

import java.util.Calendar;

/**
 * Created by ssangwoo on 2017-12-04.
 */

public class EditAlarmTimeDialog implements TimePickerDialog.OnTimeSetListener {

    private final Medicine medicine;
    private final Context context;

    public EditAlarmTimeDialog(Context context, Medicine medicine) {
        this.medicine = medicine;
        this.context = context;
    }


    public void make() {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(context, this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false).show();
    }

    private Alarm alarm;
    public void make(Alarm alarm) {
        this.alarm = alarm;
        new TimePickerDialog(context, this,
                alarm.getHour(), alarm.getMinutes(), false).show();
    }

    private void make(int hour, int minute) {
        new TimePickerDialog(context, this,
                hour, minute, false).show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, final int hour, final int minute) {
        Alarm alarm = null;
        if(this.alarm != null) { // 편집
            alarm = AppDatabaseDAO.updateAlarm(this.alarm, hour, minute);
            new AlarmController(context).cancelAlarm(alarm.getId());
        } else { // 추가
            if(AppDatabaseDAO.isExistedAlarmTime(hour, minute)) {
                new AlertDialog.Builder(context)
                        .setMessage(context.getText(R.string.error_existed_alarm_Time))
                        .setPositiveButton(context.getText(R.string.close_dialog),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        make(hour, minute);
                                        dialogInterface.cancel();
                                    }
                                })
                        .create().show();
            } else {
                alarm = AppDatabaseDAO.createAlarm(medicine, hour, minute);
            }
        }
        if(alarm != null) {
            if (alarm.isEnable()) {
                AlarmInfo alarmInfo = AppDatabaseDAO.selectTodayAlarmInfo(alarm.getId());
                new AlarmController(context).startAlarm(alarm.getDate().getTime(),
                        alarm.getId(), alarmInfo);
                Toast.makeText(context, String.format(
                        context.getString(R.string.start_alarm_toast),
                        AppDateFormat.ALARM_TIME.format(alarm.getDate())), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
