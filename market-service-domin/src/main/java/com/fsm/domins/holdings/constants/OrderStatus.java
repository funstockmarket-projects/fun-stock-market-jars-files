package com.fsm.domins.holdings.constants;

import lombok.Getter;

@Getter
public enum OrderStatus {
    COMPLETE("300", "COMPLETE"),
    PENDING("301", "PENDING"),
    CANCELLED("302", "CANCELLED"),
    FAILED("303", "FAILED");

    private final String statusCode;
    private final String status;

    private OrderStatus(String code, String status) {
        this.statusCode = code;
        this.status = status;
    }
}
