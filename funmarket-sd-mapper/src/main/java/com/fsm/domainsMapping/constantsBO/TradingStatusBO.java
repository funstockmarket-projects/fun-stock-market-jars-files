package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum TradingStatusBO {
    ACTIVE("ACTIVE", "Trading is Active"),
    SUSPENDED("SUSPENDED", "Trading is Suspended"),
    HALTED("HALTED", "Trading is Halted"),
    DELISTED("DELISTED", "Stock is Delisted"),
    UNDER_SURVEILLANCE("UNDER_SURVEILLANCE", "Stock is Under Surveillance");

    private final String statusCode;
    private final String statusDescription;

    TradingStatusBO(String code, String description) {
        this.statusCode = code;
        this.statusDescription = description;
    }
}

