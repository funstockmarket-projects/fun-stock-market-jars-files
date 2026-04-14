package com.fsm.domins.broker.constants;

import lombok.Getter;

@Getter
public enum BrokerType {

    DISCOUNT("250","DISCOUNT"),
    HYBRID("251","HYBRID"),
    FULL_SERVICE("252","FULL_SERVICE");

    private final String brokerCode;
    private final String brokerType;

    BrokerType(String code, String brokerType){
        this.brokerCode=code;
        this.brokerType=brokerType;
    }
}