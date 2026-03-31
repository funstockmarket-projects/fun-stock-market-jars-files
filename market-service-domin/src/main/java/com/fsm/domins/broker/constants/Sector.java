package com.fsm.domins.broker.constants;

public enum Sector {
    FINTECH("650", "Fintech"),
    FINANCE("651", "Finance"),
    BANKING("652", "Baking");

    private final String sectorCode;
    private final String sectorName;

    Sector(String number, String name) {
        this.sectorCode=number;
        this.sectorName=name;
    }
}
