package com.fsm.domainsMapping.businessObject.stockEngineMapping.authBO.constantsBO;

import lombok.Getter;

@Getter
public enum TokenStatusBO {
    ACTIVE("ACTIVE"),
    EXPIRED("EXPIRED");

    private final String value;

    TokenStatusBO(String value){
        this.value=value;
    }
}
