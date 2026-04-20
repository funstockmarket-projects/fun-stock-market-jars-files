package com.fsm.domins.holdings.constants;

import lombok.Getter;

@Getter
public enum OrderCategory {
    INTRADAY("MIS", "INTRADAY"),
    LONG_TERM("CNC", "LONG_TERM");

    private final String categoryCode;
    private final String categoryName;

    private OrderCategory(String code, String name) {
        this.categoryCode = code;
        this.categoryName = name;
    }
}
