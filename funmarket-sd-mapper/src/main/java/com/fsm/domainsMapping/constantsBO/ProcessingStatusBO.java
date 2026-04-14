package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum ProcessingStatusBO {
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

    ProcessingStatusBO(String value) {
        this.value = value;
    }
}

