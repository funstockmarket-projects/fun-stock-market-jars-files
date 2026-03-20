package com.fsm.domins.globalenums;

import lombok.Getter;

@Getter
public enum Days {
    SUNDAY("SUNDAY"),
    MONDAY("MONDAY"),
    TUESDAY("TUESDAY"),
    WEDNESDAY("WEDNESDAY"),
    THURSDAY("THURSDAY"),
    FRIDAY("FRIDAY"),
    SATURDAY("SATURDAY");

    private final String value;

    Days(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
