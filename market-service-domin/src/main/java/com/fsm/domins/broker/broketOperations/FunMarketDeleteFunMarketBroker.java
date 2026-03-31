package com.fsm.domins.broker.broketOperations;

import com.fsm.domins.broker.repository.BrokerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component(value = "FunMarketDeleteFunMarketBroker")
public final class FunMarketDeleteFunMarketBroker implements FunMarketBrokerRemoveMethods {

    private static final Logger log = LoggerFactory.getLogger(FunMarketDeleteFunMarketBroker.class);

    private final BrokerRepository brokerRepository;

    public FunMarketDeleteFunMarketBroker(@Qualifier(value = "BrokerRepository") BrokerRepository brokerRepository){
        this.brokerRepository=brokerRepository;
    }
    @Override
    public void RemoveBrokerByIdentifier(String identifier) {
        if(identifier.isBlank()){
            throw new IllegalArgumentException("Broker Identifier cannot be null. Fails to delete Broker.");
        }
        brokerRepository.deleteByBrokerIdentifier(identifier);
        log.info("Successfully deleted by [ identifier: {} ]", identifier);
    }
}
