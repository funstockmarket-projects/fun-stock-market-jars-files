package com.fsm.domainsMapping.businessObject.stockEngineMapping.authBO.constantsBO;

import lombok.Getter;

@Getter
public enum RecordStatusBO {
    ADDED("ADDED"),
    MODIFIED("MODIFIED");

    private final String value;

    RecordStatusBO(String value) {
        this.value = value;
    }
}
