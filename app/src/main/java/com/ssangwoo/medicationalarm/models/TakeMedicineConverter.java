package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.converter.TypeConverter;
import com.ssangwoo.medicationalarm.enums.TakeMedicineEnum;

/**
 * Created by ssangwoo on 2017-10-30.
 */

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class TakeMedicineConverter extends TypeConverter<String, TakeMedicineEnum> {
    @Override
    public String getDBValue(TakeMedicineEnum models) {
        return models == null ? null : models.name();
    }

    @Override
    public TakeMedicineEnum getModelValue(String data) {
        return TakeMedicineEnum.valueOf(data);
    }
}
