package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * Created by ssangwoo on 2017-10-27.
 */
@Table(database = AppDatabase.class)
public class Medicine extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String title = "감기약";

    @Column
    String description = "빨리 먹고 낫자";

    @Column(name = "date_from")
    Date dateFrom = new Date();

    @Column(name = "date_to")
    Date dateTo = new Date();

    List<Alarm> alarmList;

    @Column
    Date createdDate = new Date();

    @Column
    Date modifiedDate = new Date();

    public Medicine() {}

    public Medicine(String title, String description, Date dateFrom, Date dateTo) {
        this.title = title;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @OneToMany(methods = OneToMany.Method.ALL)
    public List<Alarm> getAlarmList() {
        if (alarmList == null || alarmList.isEmpty()) {
            alarmList = new Select()
                    .from(Alarm.class)
                    .where(Alarm_Table.medicine_id.eq(id))
                    .queryList();
        }
        return alarmList;
    }

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
