package com.fsm.domainsMapping.constantsBO;

public enum BrokerStatusBO {
    ACTIVE("100", "ACTIVE"),
    INACTIVE("101", "INACTIVE"),
    SUSPENDED("102", "SUSPENDED");

    private final String statusCode;
    private final String status;

    BrokerStatusBO(String code, String status) {
        this.statusCode = code;
        this.status = status;
    }
}
