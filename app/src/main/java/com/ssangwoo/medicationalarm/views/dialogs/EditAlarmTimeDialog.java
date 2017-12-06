package com.ssangwoo.medicationalarm.views.dialogs;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ssangwoo.medicationalarm.R;
import com.ssangwoo.medicationalarm.alarms.AlarmController;
import com.ssangwoo.medicationalarm.controllers.interfaces.UpdateAlarmRecyclerInterface;
import com.ssangwoo.medicationalarm.models.Alarm;
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
    private UpdateAlarmRecyclerInterface updateInterface;

    public EditAlarmTimeDialog(Context context, Medicine medicine,
                               UpdateAlarmRecyclerInterface updateInterface) {
        this.medicine = medicine;
        this.context = context;
        this.updateInterface = updateInterface;
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

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Alarm alarm = null;
        if(this.alarm != null) { // 편집
            alarm = AppDatabaseDAO.updateAlarm(this.alarm, hour, minute);
            new AlarmController(context).cancelAlarm(alarm.getMedicine().getId(), alarm.getId());
        } else { // 추가
            alarm = AppDatabaseDAO.createAlarm(medicine, hour, minute);
        }
        if(alarm.isEnable()) {
            new AlarmController(context).startAlarm(
                    alarm.getDate().getTime(), medicine.getId(), alarm.getId());
            Toast.makeText(context, String.format(
                    context.getString(R.string.start_alarm_toast),
                    AppDateFormat.ALARM_TIME.format(alarm.getDate())), Toast.LENGTH_SHORT).show();
        }
        updateInterface.update(medicine.getId());
    }
}
