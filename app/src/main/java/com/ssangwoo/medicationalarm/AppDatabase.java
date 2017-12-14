package com.ssangwoo.medicationalarm;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by ssangwoo on 2017-10-29.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "MedicationAlarmDatabase";
    public static final int VERSION = 1;
}
