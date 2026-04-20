package com.fsm.domins.stockDetails.constaunts;

import lombok.Getter;

@Getter
public enum StockStatus {
    ACTIVE("100", "ACTIVE"),
    INACTIVE("101", "INACTIVE"),
    SUSPENDED("102", "SUSPENDED");

    private final String statusCode;
    private final String status;

    StockStatus(String code, String status) {
        this.statusCode = code;
        this.status = status;
    }
}
