package com.fsm.domins.broker.constants;

import lombok.Getter;

@Getter
public enum BrokerStatus {
    ACTIVE("100", "Active"),
    INACTIVE("101", "Inactive"),
    SUSPENDED("102", "Suspended");

    private final String statusCode;
    private final String status;

    BrokerStatus(String code, String status) {
        this.statusCode = code;
        this.status = status;
    }
}
