package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum DaysBO {
    SUNDAY("SUNDAY"),
    MONDAY("MONDAY"),
    TUESDAY("TUESDAY"),
    WEDNESDAY("WEDNESDAY"),
    THURSDAY("THURSDAY"),
    FRIDAY("FRIDAY"),
    SATURDAY("SATURDAY"),
    UNKNOWN("UNKNOWN");

    private final String value;

    DaysBO(String value) {
        this.value = value;
    }

}

