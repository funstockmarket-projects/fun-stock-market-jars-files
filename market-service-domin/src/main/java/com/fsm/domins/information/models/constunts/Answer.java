package com.fsm.domins.information.models.constunts;

import lombok.Getter;

@Getter
public enum Answer {
    Y("YES"),
    N("NO");

    private final String value;

    Answer(String value) {
        this.value = value;
    }
}
