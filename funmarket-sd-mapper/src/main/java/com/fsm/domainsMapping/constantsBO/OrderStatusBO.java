package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum OrderStatusBO {
    COMPLETE("300", "COMPLETE"),
    PENDING("301", "PENDING"),
    CANCELLED("302", "CANCELLED"),
    FAILED("303", "FAILED");

    private final String statusCode;
    private final String status;

    OrderStatusBO(String code, String status) {
        this.statusCode = code;
        this.status = status;
    }
}

