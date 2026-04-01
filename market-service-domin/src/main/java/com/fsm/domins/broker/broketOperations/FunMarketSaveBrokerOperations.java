package com.fsm.domins.broker.broketOperations;


import com.fsm.dominsMapping.businessObject.brokerBO.BrokerBO;
import org.springframework.stereotype.Component;

@Component(value = "FunMarketSaveBrokerOperations")
public sealed interface FunMarketSaveBrokerOperations permits FunMarketSaveBroker {

    BrokerBO save(BrokerBO brokerBO);
}