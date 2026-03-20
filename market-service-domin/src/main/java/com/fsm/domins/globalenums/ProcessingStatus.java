package com.fsm.domins.globalenums;

import lombok.Getter;

@Getter
public enum ProcessingStatus {
    NOT_PROCESSED("NOT_PROCESSED"),
    PROCESSING("PROCESSING"),
    PROCESSED("PROCESSED"),
    FAILED("FAILED"),
    SUSPENDED("SUSPENDED"),

    MODIFICATION_PROGRESS("MODIFICATION_PROGRESS"),
    ADDING_IN_PROCESS("ADDING_IN_PROCESS"),
    REMOVING_IN_PROCESS("REMOVING_IN_PROCESS"),

    ADDED("ADDED"),
    REMOVED("REMOVED"),
    MODIFIED("MODIFIED");

    private final String value;

    ProcessingStatus(String value) {
        this.value = value;
    }
}
