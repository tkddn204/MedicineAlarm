package com.ssangwoo.medicationalarm.models;

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
        Medicine medicine = new Select().from(Medicine.class)
                .where(Medicine_Table.id.eq(medicineId)).querySingle();
        if(medicine != null) {
            medicine.load();
            for(Alarm alarm : medicine.getAlarmList()) {
                alarm.load();
                //TODO : alarmInfo 로딩되게
            }
        }
        return medicine;
    }

    public static Alarm selectAlarm(int alarmId) {
        Alarm alarm = new Select().from(Alarm.class)
                .where(Alarm_Table.id.eq(alarmId)).querySingle();
        if(alarm != null) {
            alarm.load();
        }
        return alarm;
    }

    public static List<Alarm> selectAllAlarmList() {
        return new Select().from(Alarm.class)
                .orderBy(Alarm_Table.date, true).queryList();
    }

    public static List<Alarm> selectAlarmList(int medicineId) {
        List<Alarm> alarmList = new Select().from(Alarm.class)
                .where(Alarm_Table.medicine_id.eq(medicineId))
                .orderBy(Alarm_Table.date, true).queryList();
        for(Alarm alarm : alarmList) {
            alarm.load();
        }
        return alarmList;
    }

    public static AlarmInfo selectTodayAlarmInfo(int alarmId) {
        AlarmInfo alarmInfo = new Select().from(AlarmInfo.class)
                .where(AlarmInfo_Table.alarm_id.eq(alarmId))
                .orderBy(AlarmInfo_Table.takeDate, false)
                .querySingle();
        if(alarmInfo != null) {
            alarmInfo.load();
            alarmInfo.getAlarm().load();
            alarmInfo.getAlarm().getMedicine().load();
        }
        return alarmInfo;
    }

    // 위까지가 셀렉트

    public static void nextAlarmDateUpdate(int alarmId) {
        Alarm alarm = selectAlarm(alarmId);
        nextAlarmDateUpdate(alarm);
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
        alarm.save();
    }

    public static Medicine createMedicine() {
        Medicine medicine = new Medicine();
        medicine.insert();
        return medicine;
    }

    public static Alarm createAlarm(Medicine medicine, int hour, int minutes) {
        medicine.load();

        Alarm alarm = new Alarm(hour, minutes);
        alarm.setMedicine(medicine);

        AlarmInfo alarmInfo = new AlarmInfo(alarm);
        alarmInfo.save();

        alarm.getAlarmInfoList().add(alarmInfo);

        nextAlarmDateUpdate(alarm);

        medicine.getAlarmList().add(alarm);
        medicine.update();

        return alarm;
    }

    public static void deleteMedicine(int medicineId){
        new Delete().from(Medicine.class)
                .where(Medicine_Table.id.eq(medicineId))
                .execute();
    }

    public static void deleteAlarm(int alarmId) {
        new Delete().from(Alarm.class)
                .where(Alarm_Table.id.eq(alarmId))
                .execute();
    }

    public static Alarm updateAlarm(Alarm alarm, int hour, int minute) {
        alarm.setHour(hour);
        alarm.setMinutes(minute);
        nextAlarmDateUpdate(alarm);

        return alarm;
    }

    public static void updateTakeMedicine(AlarmInfo alarmInfo,
                                          TakeMedicineEnum takeMedicineEnum) {
        alarmInfo.load();
        alarmInfo.setTakeMedicine(takeMedicineEnum);
        if(alarmInfo.getTakeMedicine().equals(TakeMedicineEnum.TAKE)) {
            alarmInfo.setTakeDate(new Date());
        }
        alarmInfo.save();
    }

    public static void updateMedicine(Medicine medicine, String title, String desc) {
        medicine.setTitle(title);
        medicine.setDescription(desc);
        medicine.setModifiedDate(new Date());
        medicine.update();
    }

    public static boolean isExistedAlarmTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Date settingDate = calendar.getTime();

        calendar.add(Calendar.MINUTE, 15);
        Date afterDate = calendar.getTime();
        calendar.add(Calendar.MINUTE, -30);
        Date beforeDate = calendar.getTime();

        if(settingDate.before(new Date())) {
            calendar.add(Calendar.DATE, 1);
            beforeDate = calendar.getTime();
            calendar.add(Calendar.MINUTE, 30);
            afterDate = calendar.getTime();
        }

        // TODO: 향후 날짜 비교 바꾸기

        Alarm alarm = new Select().from(Alarm.class)
                .where(Alarm_Table.date.lessThanOrEq(afterDate))
                .and(Alarm_Table.date.greaterThanOrEq(beforeDate))
                .querySingle();

        return alarm != null;
    }
}
