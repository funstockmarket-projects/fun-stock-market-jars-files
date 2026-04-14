package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum BrokerTypeBO {

    DISCOUNT("250","DISCOUNT"),
    HYBRID("251","HYBRID"),
    FULL_SERVICE("252","FULL_SERVICE");

    private final String brokerCode;
    private final String brokerType;

    BrokerTypeBO(String code, String brokerType){
        this.brokerCode=code;
        this.brokerType=brokerType;
    }
}
