package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum SectorBO {
    FINTECH("650", "Fintech"),
    FINANCE("651", "Finance"),
    BANKING("652", "Banking");

    private final String sectorCode;
    private final String sectorName;

    SectorBO(String number, String name) {
        this.sectorCode=number;
        this.sectorName=name;
    }
}

