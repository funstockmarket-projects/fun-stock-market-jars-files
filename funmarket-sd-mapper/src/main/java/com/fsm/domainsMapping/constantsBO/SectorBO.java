package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum SectorBO {
    FINTECH("650", "FINTECH"),
    FINANCE("651", "FINANCE"),
    BANKING("652", "BANKING");

    private final String sectorCode;
    private final String sectorName;

    SectorBO(String number, String name) {
        this.sectorCode=number;
        this.sectorName=name;
    }
}

