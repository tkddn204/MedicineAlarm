package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;

import java.util.Date;

/**
 * Created by ssangwoo on 2017-12-03.
 */

@Table(database = AppDatabase.class)
public class AlarmInfo extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @ForeignKey(stubbedRelationship = true)
    Alarm alarm;

    @Column
    Date takeDate = new Date();

    @Column(typeConverter = TakeMedicineConverter.class)
    TakeMedicineEnum takeMedicine = TakeMedicineEnum.NOT_YET_TAKE;

    public AlarmInfo() {}

    public AlarmInfo(Alarm alarm) {
        this.alarm = alarm;
    }

    public AlarmInfo(Alarm alarm, Date takeDate) {
        this.alarm = alarm;
        this.takeDate = takeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public Date getTakeDate() {
        return takeDate;
    }

    public void setTakeDate(Date takeDate) {
        this.takeDate = takeDate;
    }

    public TakeMedicineEnum getTakeMedicine() {
        return takeMedicine;
    }

    public void setTakeMedicine(TakeMedicineEnum takeMedicine) {
        this.takeMedicine = takeMedicine;
    }
}
