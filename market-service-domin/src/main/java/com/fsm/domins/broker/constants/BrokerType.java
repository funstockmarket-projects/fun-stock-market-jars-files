package com.fsm.domins.broker.constants;

import lombok.Getter;

@Getter
public enum BrokerType {

    DISCOUNT("250","Discount"),
    HYBRID("251","Hybrid"),
    FULL_SERVICE("252","Full Service");

    private final String brokerCode;
    private final String brokerType;

    BrokerType(String code, String brokerType){
        this.brokerCode=code;
        this.brokerType=brokerType;
    }
}