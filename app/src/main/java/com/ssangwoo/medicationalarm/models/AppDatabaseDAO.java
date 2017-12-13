package com.ssangwoo.medicationalarm.models;

import android.os.Handler;
import android.os.Looper;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
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

    public static Alarm selectAlarm(int alarmId) {
        return new Select().from(Alarm.class)
                .where(Alarm_Table.id.eq(alarmId)).querySingle();
    }

    public static List<Alarm> selectAllAlarmList() {
        return new Select().from(Alarm.class)
                .orderBy(Alarm_Table.date, true).queryList();
    }

    public static List<Alarm> selectAlarmList(int medicineId) {
        return new Select().from(Alarm.class)
                .where(Alarm_Table.medicine_id.eq(medicineId))
                .orderBy(Alarm_Table.date, true).queryList();
    }

    public static AlarmInfo selectTodayAlarmInfo(int alarmId) {
        return new Select().from(AlarmInfo.class)
                .where(AlarmInfo_Table.alarm_id.eq(alarmId))
                .orderBy(AlarmInfo_Table.takeDate, false)
                .querySingle();
    }

    // 위까지가 셀렉트

    public static void nextAlarmDateUpdate(int alarmId) {
        FlowContentObserver observer = new FlowContentObserver();
        observer.beginTransaction();

        Alarm alarm = selectAlarm(alarmId);
        nextAlarmDateUpdate(alarm);
        alarm.update();

        observer.endTransactionAndNotify();
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
        alarm.update();
    }

    public static Alarm createAlarm(Medicine medicine, int hour, int minutes) {
        FlowContentObserver observer = new FlowContentObserver();
        observer.beginTransaction();

        Alarm alarm = new Alarm(medicine, hour, minutes);
        alarm.save();
        nextAlarmDateUpdate(alarm);

        AlarmInfo alarmInfo = new AlarmInfo(alarm);
        alarm.getAlarmInfoList().add(alarmInfo);

        alarmInfo.save();

        observer.endTransactionAndNotify();
        return alarm;
    }

    public static void deleteMedicine(int medicineId){
        FlowContentObserver observer = new FlowContentObserver();
        observer.beginTransaction();
        new Delete().from(Medicine.class)
                .where(Medicine_Table.id.eq(medicineId))
                .execute();
        observer.endTransactionAndNotify();
    }

    public static void deleteAlarm(int alarmId) {
        FlowContentObserver observer = new FlowContentObserver();
        observer.beginTransaction();

        new Delete().from(Alarm.class)
                .where(Alarm_Table.id.eq(alarmId))
                .execute();

        observer.endTransactionAndNotify();
    }

    public static Alarm updateAlarm(Alarm alarm, int hour, int minute) {
        FlowContentObserver observer = new FlowContentObserver();
        observer.beginTransaction();

        alarm.setHour(hour);
        alarm.setMinutes(minute);
        nextAlarmDateUpdate(alarm);

        observer.endTransactionAndNotify();
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

    public static void updateMedicine(Medicine medicine, String title, String desc) {
        FlowContentObserver observer = new FlowContentObserver();
        observer.beginTransaction();

        medicine.setTitle(title);
        medicine.setDescription(desc);
        medicine.setModifiedDate(new Date());
        medicine.update();

        observer.endTransactionAndNotify();
    }
}
