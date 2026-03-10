package com.fsm.domins.information.models.constunts;

import lombok.Getter;

@Getter
public enum ProcessingStatus {
    NOT_PROCESSED("NOT_PROCESSED"),
    PROCESSING("PROCESSING"),
    PROCESSED("PROCESSED"),
    FAILED("FAILED"),
    SUSPENDED("SUSPENDED"),;

    private final String value;

    ProcessingStatus(String value) {
        this.value = value;
    }
}
