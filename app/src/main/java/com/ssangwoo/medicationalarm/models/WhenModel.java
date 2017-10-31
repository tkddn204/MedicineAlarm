package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by ssangwoo on 2017-10-31.
 */

@Table(database = AppDatabase.class)
public class WhenModel extends BaseModel {
    @PrimaryKey
    int id;

    @Column
    boolean isMorning;

    @Column
    boolean isAfternoon;

    @Column
    boolean isDinner;

    @Column
    Date morningAlarm = new Date();

    @Column
    Date afternoonAlarm = new Date();

    @Column
    Date dinnerAlarm = new Date();

    public WhenModel() {}

    public WhenModel(boolean isMorning, boolean isAfternoon, boolean isDinner, Date morningAlarm, Date afternoonAlarm, Date dinnerAlarm) {
        this.isMorning = isMorning;
        this.isAfternoon = isAfternoon;
        this.isDinner = isDinner;
        this.morningAlarm = morningAlarm;
        this.afternoonAlarm = afternoonAlarm;
        this.dinnerAlarm = dinnerAlarm;
    }

    public WhenModel(boolean isMorning, boolean isAfternoon, boolean isDinner) {
        this.isMorning = isMorning;
        this.isAfternoon = isAfternoon;
        this.isDinner = isDinner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMorning() {
        return isMorning;
    }

    public void setMorning(boolean morning) {
        isMorning = morning;
    }

    public boolean isAfternoon() {
        return isAfternoon;
    }

    public void setAfternoon(boolean afternoon) {
        isAfternoon = afternoon;
    }

    public boolean isDinner() {
        return isDinner;
    }

    public void setDinner(boolean dinner) {
        isDinner = dinner;
    }

    public Date getMorningAlarm() {
        return morningAlarm;
    }

    public void setMorningAlarm(Date morningAlarm) {
        this.morningAlarm = morningAlarm;
    }

    public Date getAfternoonAlarm() {
        return afternoonAlarm;
    }

    public void setAfternoonAlarm(Date afternoonAlarm) {
        this.afternoonAlarm = afternoonAlarm;
    }

    public Date getDinnerAlarm() {
        return dinnerAlarm;
    }

    public void setDinnerAlarm(Date dinnerAlarm) {
        this.dinnerAlarm = dinnerAlarm;
    }
}
