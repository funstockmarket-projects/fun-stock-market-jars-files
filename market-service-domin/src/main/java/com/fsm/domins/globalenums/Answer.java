package com.fsm.domins.globalenums;

import lombok.Getter;

@Getter
public enum Answer {
    Y("Y"),
    N("N");

    private final String value;

    Answer(String value) {
        this.value = value;
    }
}
