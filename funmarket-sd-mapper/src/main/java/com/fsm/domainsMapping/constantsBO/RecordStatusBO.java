package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum RecordStatusBO {
    ADDED("ADDED"),
    REMOVED("REMOVED"),
    MODIFIED("MODIFIED"),
    UNKNOWN("UNKNOWN"),
    FILE_DATA_MODIFIED("FILE_DATA_MODIFIED");

    private final String value;

    RecordStatusBO(String value) {
        this.value = value;
    }

}

