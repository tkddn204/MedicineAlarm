package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.TypeConverter;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ssangwoo.medicationalarm.AppDatabase;
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
    int pendingRequestNumber;

    @Column
    Date takeDate = new Date();

    @Column(typeConverter = TakeMedicineConverter.class)
    TakeMedicineEnum takeMedicine;

    public AlarmInfo() { }

    public AlarmInfo(TakeMedicineEnum takeMedicine) {
        this.takeMedicine = takeMedicine;
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
        this.pendingRequestNumber = 5378 *
                (alarm.getMedicine().getId()+1) *
                (alarm.getId()+1) + id;
    }

    public int getPendingRequestNumber() {
        return pendingRequestNumber;
    }

    public void setPendingRequestNumber(int pendingRequestNumber) {
        this.pendingRequestNumber = pendingRequestNumber;
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
