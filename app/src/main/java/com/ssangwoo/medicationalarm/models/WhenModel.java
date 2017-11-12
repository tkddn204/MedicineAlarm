package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;

import java.util.Date;

/**
 * Created by ssangwoo on 2017-10-31.
 */

@Table(database = AppDatabase.class)
@ManyToMany(referencedTable = MedicineModel.class)
public class WhenModel extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    boolean isBreakfast;

    @Column
    boolean isLunch;

    @Column
    boolean isDinner;

    @Column(typeConverter = TakeMedicineConverter.class)
    TakeMedicineEnum takeBreakfast = TakeMedicineEnum.NOT_YET_TAKE;

    @Column(typeConverter = TakeMedicineConverter.class)
    TakeMedicineEnum takeLunch = TakeMedicineEnum.NOT_YET_TAKE;

    @Column(typeConverter = TakeMedicineConverter.class)
    TakeMedicineEnum takeDinner = TakeMedicineEnum.NOT_YET_TAKE;

    @Column
    boolean isBreakfastAlarm;

    @Column
    boolean isLunchAlarm;

    @Column
    boolean isDinnerAlarm;

    @Column
    Date breakfastAlarm = new Date();

    @Column
    Date lunchAlarm = new Date();

    @Column
    Date dinnerAlarm = new Date();

    public WhenModel() {}

    public WhenModel(boolean isBreakfast, boolean isLunch, boolean isDinner) {
        this.isBreakfast = isBreakfast;
        this.isLunch = isLunch;
        this.isDinner = isDinner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBreakfast() {
        return isBreakfast;
    }

    public void setBreakfast(boolean breakfast) {
        isBreakfast = breakfast;
    }

    public boolean isLunch() {
        return isLunch;
    }

    public void setLunch(boolean lunch) {
        isLunch = lunch;
    }

    public boolean isDinner() {
        return isDinner;
    }

    public void setDinner(boolean dinner) {
        isDinner = dinner;
    }

    public boolean isBreakfastAlarm() {
        return isBreakfastAlarm;
    }

    public void setBreakfastAlarm(boolean breakfastAlarm) {
        isBreakfastAlarm = breakfastAlarm;
    }

    public boolean isLunchAlarm() {
        return isLunchAlarm;
    }

    public void setLunchAlarm(boolean lunchAlarm) {
        isLunchAlarm = lunchAlarm;
    }

    public boolean isDinnerAlarm() {
        return isDinnerAlarm;
    }

    public void setDinnerAlarm(boolean dinnerAlarm) {
        isDinnerAlarm = dinnerAlarm;
    }

    public Date getBreakfastAlarm() {
        return breakfastAlarm;
    }

    public void setBreakfastAlarm(Date breakfastAlarm) {
        this.breakfastAlarm = breakfastAlarm;
    }

    public Date getLunchAlarm() {
        return lunchAlarm;
    }

    public void setLunchAlarm(Date lunchAlarm) {
        this.lunchAlarm = lunchAlarm;
    }

    public Date getDinnerAlarm() {
        return dinnerAlarm;
    }

    public void setDinnerAlarm(Date dinnerAlarm) {
        this.dinnerAlarm = dinnerAlarm;
    }
}
