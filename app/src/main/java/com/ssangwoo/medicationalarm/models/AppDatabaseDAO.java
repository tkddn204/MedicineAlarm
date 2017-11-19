package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by user on 2017-11-19.
 */

public class AppDatabaseDAO {
    public static List<Medicine> selectMedicineList() {
        return new Select().from(Medicine.class).queryList();
    }

    public static Medicine selectMedicine(int medicineId) {
        return new Select().from(Medicine.class)
                .where(Medicine_Table.id.eq(medicineId)).querySingle();
    }

    public static void deleteMedicine(int medicineId){
        new Delete().from(Medicine.class)
                .where(Medicine_Table.id.eq(medicineId))
                .execute();
    }
}
