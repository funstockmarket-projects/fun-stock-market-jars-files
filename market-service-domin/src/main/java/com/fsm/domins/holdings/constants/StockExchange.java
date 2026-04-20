package com.fsm.domins.holdings.constants;

import lombok.Getter;

@Getter
public enum StockExchange {
    BSE("BSE", "Bombay Stock Exchange"),
    NSE("NSE", "National Stock Exchange");

    private final String exchangeCode;
    private final String exchangeName;

    private StockExchange(String code, String name) {
        this.exchangeCode = code;
        this.exchangeName = name;
    }
}
