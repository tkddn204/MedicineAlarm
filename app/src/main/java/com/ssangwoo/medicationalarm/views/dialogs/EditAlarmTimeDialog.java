package com.ssangwoo.medicationalarm.views.dialogs;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import com.ssangwoo.medicationalarm.controllers.interfaces.UpdateAlarmRecyclerInterface;
import com.ssangwoo.medicationalarm.models.Alarm;
import com.ssangwoo.medicationalarm.models.AppDatabaseDAO;
import com.ssangwoo.medicationalarm.models.Medicine;

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
        if(alarm != null) { // 편집
            AppDatabaseDAO.updateAlarm(alarm, hour, minute);
        } else { // 추가
            AppDatabaseDAO.createAlarm(medicine, hour, minute);
        }
        updateInterface.update(medicine.getId());
    }
}
