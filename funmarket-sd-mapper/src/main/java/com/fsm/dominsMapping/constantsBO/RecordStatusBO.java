package com.fsm.dominsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum RecordStatusBO {
    ADDED("added"),
    REMOVED("removed"),
    MODIFIED("modified"),
    FILE_DATA_MODIFIED("file_data_modified");

    private final String value;

    RecordStatusBO(String value) {
        this.value = value;
    }

}
