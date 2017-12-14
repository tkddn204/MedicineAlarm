package com.ssangwoo.medicationalarm.enums;

/**
 * Created by ssangwoo on 2017-11-07.
 */

public enum NotificationActionEnum {
    TAKE_BUTTON_CLICK_ACTION("com.ssangwoo.checktakingmedicine.action.take_medicine"),
    DO_NOT_TAKE_BUTTON_CLICK_ACTION("com.ssangwoo.checktakingmedicine.action.do_not_take_medicine"),
    RE_ALARM_BUTTON_CLICK_ACTION("com.ssangwoo.checktakingmedicine.action.re_alarm");

    private String action;

    NotificationActionEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
