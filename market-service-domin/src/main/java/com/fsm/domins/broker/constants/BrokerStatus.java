package com.fsm.domins.broker.constants;

import lombok.Getter;

@Getter
public enum BrokerStatus {
    ACTIVE("100", "ACTIVE"),
    INACTIVE("101", "INACTIVE"),
    SUSPENDED("102", "SUSPENDED");

    private final String statusCode;
    private final String status;

    BrokerStatus(String code, String status) {
        this.statusCode = code;
        this.status = status;
    }
}
