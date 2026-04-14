package com.fsm.domins.broker.constants;

import lombok.Getter;

@Getter
public enum Sector {
    FINTECH("650", "FINTECH"),
    FINANCE("651", "FINANCE"),
    BANKING("652", "BANKING");

    private final String sectorCode;
    private final String sectorName;

    Sector(String number, String name) {
        this.sectorCode=number;
        this.sectorName=name;
    }
}
