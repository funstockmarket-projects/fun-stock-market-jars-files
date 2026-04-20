package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum StockStatusBO {
    ACTIVE("100", "ACTIVE"),
    INACTIVE("101", "INACTIVE"),
    SUSPENDED("102", "SUSPENDED");

    private final String statusCode;
    private final String status;

    StockStatusBO(String code, String status) {
        this.statusCode = code;
        this.status = status;
    }
}
