package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 2017-11-19.
 */

@Table(database = AppDatabase.class)
public class Alarm extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @ForeignKey(stubbedRelationship = true)
    Medicine medicine;

    @Column
    Date date = new Date();

    @Column
    int hour;

    @Column
    int minutes;

    @Column(name = "enable")
    boolean isEnable = true;

    List<AlarmInfo> alarmInfoList;

    public Alarm() {}

    public Alarm(Medicine medicine, int hour, int minutes) {
        this.medicine = medicine;
        this.hour = hour;
        this.minutes = minutes;
    }

    public int getId() {
        return id;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    @OneToMany(methods = OneToMany.Method.ALL)
    public List<AlarmInfo> getAlarmInfoList() {
        if (alarmInfoList == null || alarmInfoList.isEmpty()) {
            alarmInfoList = new Select()
                    .from(AlarmInfo.class)
                    .where(AlarmInfo_Table.alarm_id.eq(id))
                    .queryList();
        }
        return alarmInfoList;
    }

    public void setAlarmInfoList(List<AlarmInfo> alarmInfoList) {
        this.alarmInfoList = alarmInfoList;
    }

    @Override
    public String toString() {
        SimpleDateFormat alarmTimeFormat
                = new SimpleDateFormat("aa hh:mm", Locale.KOREA);
        return alarmTimeFormat.format(date);
    }
}
