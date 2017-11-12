package com.ssangwoo.medicationalarm.enums;

/**
 * Created by ssangwoo on 2017-11-12.
 */

public enum SettingTypeEnum {
    SWITCH(Constants.SWITCH_VALUE),
    SELECT(Constants.SELECT_VALUE);

    private final int value;

    SettingTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static class Constants {
        static final int SWITCH_VALUE = 101;
        static final int SELECT_VALUE = 102;
    }
}
