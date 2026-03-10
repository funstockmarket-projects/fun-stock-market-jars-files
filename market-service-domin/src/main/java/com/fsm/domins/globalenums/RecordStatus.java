package com.fsm.domins.globalenums;

public enum RecordStatus {
    ADDED("added"),
    REMOVED("removed"),
    MODIFIED("modified"),
    FILE_DATA_MODIFIED("file_data_modified");

    private final String value;

    RecordStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
