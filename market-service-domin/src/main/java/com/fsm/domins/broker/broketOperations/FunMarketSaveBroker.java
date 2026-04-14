package com.fsm.domins.broker.broketOperations;

import com.fsm.domainsMapping.businessObject.brokerBO.BrokerBO;
import com.fsm.domins.broker.models.Broker;
import com.fsm.domins.broker.repository.BrokerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.fsm.domins.broker.mapper.BrokerMapper;

@Component(value = "FunMarketSaveBroker")
@RequiredArgsConstructor
public final class FunMarketSaveBroker implements FunMarketSaveBrokerOperations {

    private static final Logger log = LoggerFactory.getLogger(FunMarketSaveBroker.class);

    private final BrokerRepository brokerRepository;

    @Override
    public BrokerBO save(BrokerBO brokerBO) {
        log.info("Saving broker with identifier: {}", brokerBO.getBrokerIdentifier());

        Broker broker = BrokerMapper.bOToBroker(brokerBO);

        try {
            broker = brokerRepository.save(broker);
            log.info("Successfully saved broker with [ UUID: {}, BrokerName: {} ]", broker.brokerUUid(), broker.brokerName());
        } catch (Exception e) {
            log.error("Error saving broker with [ identifier: {} ]", brokerBO.getBrokerIdentifier(), e);
            throw new RuntimeException("Failed to save broker", e);
        }

        return BrokerMapper.BrokerToBO(broker);
    }
}
