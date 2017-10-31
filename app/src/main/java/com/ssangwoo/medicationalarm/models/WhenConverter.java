package com.ssangwoo.medicationalarm.models;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.util.ArrayList;

/**
 * Created by ssangwoo on 2017-10-30.
 */

@Deprecated
@com.raizlabs.android.dbflow.annotation.TypeConverter
public class WhenConverter extends TypeConverter<String, WhenEnum[]> {
    @Override
    public String getDBValue(WhenEnum[] models) {
        if(models == null) {
            return null;
        }
        if(models[0].getWhenName().equals("")) {
            return "";
        }

        StringBuilder whens = new StringBuilder();
        for (WhenEnum when : models) {
            whens.append(when);
            whens.append(",");
        }
        whens.deleteCharAt(whens.length()-1);
        return whens.toString();
    }

    @Override
    public WhenEnum[] getModelValue(String data) {
        ArrayList<WhenEnum> whenEnums = new ArrayList<>();
        if(data.equals("")) {
            whenEnums.add(WhenEnum.EMPTY);
        } else {
            String[] dataSplit = data.split(",");
            for (String dataItem : dataSplit) {
                whenEnums.add(WhenEnum.valueOf(dataItem));
            }
        }
        return (WhenEnum[]) whenEnums.toArray();
    }
}
