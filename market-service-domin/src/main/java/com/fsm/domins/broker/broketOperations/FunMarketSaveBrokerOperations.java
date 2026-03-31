package com.fsm.domins.broker.broketOperations;

import com.fsm.domins.broker.models.BrokerBO;
import org.springframework.stereotype.Component;

@Component(value = "FunMarketSaveBrokerOperations")
public sealed interface FunMarketSaveBrokerOperations permits FunMarketSaveBroker {

    BrokerBO save(BrokerBO brokerBO);
}