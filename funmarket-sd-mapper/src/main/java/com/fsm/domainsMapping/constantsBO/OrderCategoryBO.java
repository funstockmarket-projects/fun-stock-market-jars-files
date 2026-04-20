package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum OrderCategoryBO {
    INTRADAY("MIS", "INTRADAY"),
    LONG_TERM("CNC", "LONG_TERM");

    private final String categoryCode;
    private final String categoryName;

    OrderCategoryBO(String code, String name) {
        this.categoryCode = code;
        this.categoryName = name;
    }
}

