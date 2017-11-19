package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    String name;

    @Column
    Date date = new Date();

    @Column(name = "enable")
    boolean isEnable = true;

    public Alarm() {}

    public Alarm(Medicine medicine, String name, Date date, boolean isEnable) {
        this.medicine = medicine;
        this.name = name;
        this.date = date;
        this.isEnable = isEnable;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    @Override
    public String toString() {
        SimpleDateFormat alarmTimeFormat
                = new SimpleDateFormat("aa hh:mm", Locale.KOREA);
        return alarmTimeFormat.format(date);
    }
}
