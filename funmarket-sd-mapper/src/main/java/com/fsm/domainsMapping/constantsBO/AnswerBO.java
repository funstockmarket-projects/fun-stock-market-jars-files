package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum AnswerBO {
    Y("Y"),
    N("N");

    private final String value;

    AnswerBO(String value) {
        this.value = value;
    }
}

