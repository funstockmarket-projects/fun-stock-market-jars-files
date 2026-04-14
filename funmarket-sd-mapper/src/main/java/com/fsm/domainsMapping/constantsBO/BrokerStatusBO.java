package com.fsm.domainsMapping.constantsBO;

public enum BrokerStatusBO {
    ACTIVE("100", "Active"),
    INACTIVE("101", "Inactive"),
    SUSPENDED("102", "Suspended");

    private final String statusCode;
    private final String status;

    BrokerStatusBO(String code, String status) {
        this.statusCode = code;
        this.status = status;
    }
}
