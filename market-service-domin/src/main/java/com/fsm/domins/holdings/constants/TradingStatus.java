package com.fsm.domins.holdings.constants;

import lombok.Getter;

@Getter
public enum TradingStatus {
    ACTIVE("ACTIVE", "Trading is Active"),
    SUSPENDED("SUSPENDED", "Trading is Suspended"),
    HALTED("HALTED", "Trading is Halted"),
    DELISTED("DELISTED", "Stock is Delisted"),
    UNDER_SURVEILLANCE("UNDER_SURVEILLANCE", "Stock is Under Surveillance");

    private final String statusCode;
    private final String statusDescription;

    private TradingStatus(String code, String description) {
        this.statusCode = code;
        this.statusDescription = description;
    }
}
