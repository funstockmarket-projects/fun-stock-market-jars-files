package com.fsm.domins.broker.broketOperations;

import org.springframework.stereotype.Component;

@Component(value = "FunMarketBrokerRemoveMethods")
public sealed interface FunMarketBrokerRemoveMethods permits FunMarketDeleteFunMarketBroker {

    void RemoveBrokerByIdentifier(String identifier);
}
