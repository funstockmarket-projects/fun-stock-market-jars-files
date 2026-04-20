package com.fsm.domins.holdings.constants;

import lombok.Getter;

@Getter
public enum OrderWindow {
    BUY("200", "BUY"),
    SELL("201", "SELL"),
    SUSPEND("202", "SUSPEND");

    private final String windowCode;
    private final String window;

    private OrderWindow(String code, String window) {
        this.windowCode = code;
        this.window = window;
    }
}
