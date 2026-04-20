package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum OrderWindowBO {
    BUY("200", "BUY"),
    SELL("201", "SELL"),
    SUSPEND("202", "SUSPEND");

    private final String windowCode;
    private final String window;

    OrderWindowBO(String code, String window) {
        this.windowCode = code;
        this.window = window;
    }
}

