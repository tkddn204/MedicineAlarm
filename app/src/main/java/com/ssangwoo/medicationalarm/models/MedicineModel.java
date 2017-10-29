package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ssangwoo on 2017-10-27.
 */
@Table(database = AppDatabase.class)
public class MedicineModel extends BaseModel {
    @Column
    @PrimaryKey
    int id;

    @Column
    String title;

    @Column
    String content;


}
