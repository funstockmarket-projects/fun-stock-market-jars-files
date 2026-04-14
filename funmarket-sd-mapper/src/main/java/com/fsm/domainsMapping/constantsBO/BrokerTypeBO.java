package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum BrokerTypeBO {

    DISCOUNT("250","Discount"),
    HYBRID("251","Hybrid"),
    FULL_SERVICE("252","Full_Service");

    private final String brokerCode;
    private final String brokerType;

    BrokerTypeBO(String code, String brokerType){
        this.brokerCode=code;
        this.brokerType=brokerType;
    }
}
