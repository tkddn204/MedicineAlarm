package com.ssangwoo.medicationalarm.enums;

import com.ssangwoo.medicationalarm.R;

/**
 * Created by ssangwoo on 2017-11-07.
 */

public enum NotificationActionEnum {
    TAKE_BUTTON_CLICK_ACTION(R.id.button_take_medicine,
            "com.ssangwoo.checktakingmedicine.action.take_medicine"),
    DO_NOT_TAKE_BUTTON_CLICK_ACTION(R.id.button_do_not_take_medicine,
            "com.ssangwoo.checktakingmedicine.action.do_not_take_medicine"),
    RE_ALARM_BUTTON_CLICK_ACTION(R.id.button_re_alarm,
            "com.ssangwoo.checktakingmedicine.action.re_alarm");
//    BREAKFAST_BUTTON_CLICK_ACTION(R.id.keep_alarm_breakfast,
//            "com.ssangwoo.checktakingmedicine.action.breakfast_click"),
//    LUNCH_BUTTON_CLICK_ACTION(R.id.keep_alarm_lunch,
//            "com.ssangwoo.checktakingmedicine.action.lunch_click"),
//    DINNER_BUTTON_CLICK_ACTION(R.id.keep_alarm_dinner,
//            "com.ssangwoo.checktakingmedicine.action.dinner_click");

    private int mappingId;
    private String action;

    NotificationActionEnum(int mappingId, String action) {
        this.mappingId = mappingId;
        this.action = action;
    }

    public int getMappingId() {
        return mappingId;
    }

    public String getAction() {
        return action;
    }
}
