package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.ssangwoo.medicationalarm.alarms.AlarmController;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;

import java.util.Calendar;
import java.util.Date;
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
        return new Select().from(Alarm.class)
                .orderBy(Alarm_Table.date, true).queryList();
    }

    public static List<Alarm> selectAlarmList(int medicineId) {
        return new Select().from(Alarm.class)
                .where(Alarm_Table.medicine_id.eq(medicineId))
                .orderBy(Alarm_Table.date, true).queryList();
    }

    public static void nextAlarmDateUpdate(Alarm alarm) {
        Calendar changeCalendar = Calendar.getInstance();
        long nowTime = changeCalendar.getTimeInMillis();

        changeCalendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        changeCalendar.set(Calendar.MINUTE, alarm.getMinutes());
        changeCalendar.set(Calendar.SECOND, 0);
        if(changeCalendar.getTimeInMillis() <= nowTime) {
            changeCalendar.add(Calendar.DATE, 1);
        }

        alarm.setDate(changeCalendar.getTime());
    }

    public static void nextAlarmDateUpdate(int alarmId) {
        Alarm alarm = selectAlarm(alarmId);
        nextAlarmDateUpdate(alarm);
        alarm.update();
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

    public static Alarm createAlarm(Medicine medicine, int hour, int minutes) {
        Alarm alarm = new Alarm(medicine, hour, minutes);
        nextAlarmDateUpdate(alarm);
        alarm.save();

        AlarmInfo alarmInfo = new AlarmInfo(alarm);
        alarmInfo.save();

        return alarm;
    }

    public static Alarm updateAlarm(Alarm alarm, int hour, int minute) {
        alarm.setHour(hour);
        alarm.setMinutes(minute);
        nextAlarmDateUpdate(alarm);
        alarm.update();
        return alarm;
    }

    public static void updateTakeMedicine(AlarmInfo alarmInfo,
                                          TakeMedicineEnum takeMedicineEnum) {
        alarmInfo.setTakeMedicine(takeMedicineEnum);
        if(alarmInfo.getTakeMedicine().equals(TakeMedicineEnum.TAKE)) {
            alarmInfo.setTakeDate(new Date());
        }
        alarmInfo.update();
    }
}
