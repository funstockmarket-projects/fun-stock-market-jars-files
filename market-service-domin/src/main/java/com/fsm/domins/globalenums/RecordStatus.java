package com.fsm.domins.globalenums;

import lombok.Getter;

@Getter
public enum RecordStatus {
    ADDED("ADDED"),
    REMOVED("REMOVED"),
    MODIFIED("MODIFIED"),
    UNKNOWN("UNKNOWN"),
    FILE_DATA_MODIFIED("FILE_DATA_MODIFIED");

    private final String value;

    RecordStatus(String value) {
        this.value = value;
    }

}
