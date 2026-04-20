package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum StockExchangeBO {
    BSE("BSE", "Bombay Stock Exchange"),
    NSE("NSE", "National Stock Exchange");

    private final String exchangeCode;
    private final String exchangeName;

    StockExchangeBO(String code, String name) {
        this.exchangeCode = code;
        this.exchangeName = name;
    }
}

