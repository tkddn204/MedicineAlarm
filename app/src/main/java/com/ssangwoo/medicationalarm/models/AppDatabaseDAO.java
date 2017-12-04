package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.property.PropertyFactory;
import com.ssangwoo.medicationalarm.alarms.AlarmController;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;
import com.ssangwoo.medicationalarm.views.activities.ShowMedicineActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by user on 2017-11-19.
 */

public class AppDatabaseDAO {
    public static List<Medicine> selectMedicineList() {
        return new Select().from(Medicine.class).queryList();
    }

    public static Medicine selectMedicine(int medicineId) {
        return new Select().from(Medicine.class)
                .where(Medicine_Table.id.eq(medicineId)).querySingle();
    }

    public static void deleteMedicine(int medicineId){
        new Delete().from(Medicine.class)
                .where(Medicine_Table.id.eq(medicineId))
                .execute();
    }

    public static Alarm selectAlarm(int alarmId) {
        return new Select().from(Alarm.class)
                .where(Alarm_Table.id.eq(alarmId)).querySingle();
    }

    public static List<Alarm> selectAlarmList() {
        return new Select().from(Alarm.class).queryList();
    }

    public static List<Alarm> selectAlarmList(int medicineId) {
        return new Select().from(Alarm.class)
                .where(Alarm_Table.medicine_id.eq(medicineId))
                .queryList();
    }

    public static void nextAlarmUpdate(Alarm alarm) {
        Calendar changeCalendar = Calendar.getInstance();
        long nowTime = changeCalendar.getTimeInMillis();

        changeCalendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        changeCalendar.set(Calendar.MINUTE, alarm.getMinutes());
        changeCalendar.set(Calendar.SECOND, 0);
        if(changeCalendar.getTimeInMillis() <= nowTime) {
            changeCalendar.add(Calendar.DATE, 1);
        }

        alarm.setDate(changeCalendar.getTime());
        alarm.update();
    }

    public static void nextAlarmUpdate(int alarmId) {
        Alarm alarm = selectAlarm(alarmId);
        nextAlarmUpdate(alarm);
    }

    public static void deleteAlarm(int alarmId) {
        new Delete().from(Alarm.class)
                .where(Alarm_Table.id.eq(alarmId))
                .execute();
    }

    public static AlarmInfo selectTodayAlarmInfo(int alarmId) {
        return new Select().from(AlarmInfo.class)
                .where(AlarmInfo_Table.alarm_id.eq(alarmId))
                .orderBy(AlarmInfo_Table.takeDate, false)
                .querySingle();
    }

    public static void createAlarm(Medicine medicine, int hour, int minutes) {
        Alarm alarm = new Alarm(medicine, hour, minutes);
        nextAlarmUpdate(alarm);
        alarm.save();

        AlarmInfo alarmInfo = new AlarmInfo(alarm);
        alarmInfo.save();
    }

    public static void updateAlarm(Alarm alarm, int hour, int minute) {
        // TODO : 이전 알람 취소
        alarm.setHour(hour);
        alarm.setMinutes(minute);
        nextAlarmUpdate(alarm);
        alarm.update();
    }

    public static void updateTakeMedicine(AlarmInfo alarmInfo,
                                          TakeMedicineEnum takeMedicineEnum) {
        alarmInfo.setTakeMedicine(takeMedicineEnum);
        alarmInfo.update();
    }
}
