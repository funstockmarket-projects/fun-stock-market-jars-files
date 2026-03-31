package com.fsm.domins.broker.broketOperations;

import com.fsm.domins.broker.constants.BrokerType;
import com.fsm.domins.broker.constants.Depository;
import com.fsm.domins.broker.constants.Sector;
import com.fsm.domins.broker.models.BrokerBO;
import com.fsm.domins.broker.models.Broker;
import com.fsm.domins.broker.repository.BrokerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.fsm.domins.broker.mapper.BrokerMapper;

import java.util.List;
import java.util.Optional;

@Component(value = "FunMarketBrokerRetrievals")
@RequiredArgsConstructor
public final class FunMarketBrokerRetrievals implements FunMarketBrokerRetrievalMethods {

    private final BrokerRepository brokerRepository;

    @Override
    public BrokerBO findByBrokerUUid(String brokerName) {
        Optional<Broker> broker = brokerRepository.findByBrokerUUid(brokerName);
        return broker.map(BrokerMapper::BrokerToBO).orElse(null);
    }

    @Override
    public BrokerBO findByBrokerIdentifier(String brokerIdentifier) {
        Optional<Broker> broker = brokerRepository.findByBrokerIdentifier(brokerIdentifier);
        return broker.map(BrokerMapper::BrokerToBO).orElse(null);
    }

    @Override
    public List<BrokerBO> findByDepository(Depository depository) {
        List<Broker> broker = brokerRepository.findByDepository(depository);
        return broker.stream().map(BrokerMapper::BrokerToBO).toList();
    }

    @Override
    public List<BrokerBO> findByType(BrokerType brokerType) {
        List<Broker> broker = brokerRepository.findByType(brokerType);
        return broker.stream().map(BrokerMapper::BrokerToBO).toList();
    }

    @Override
    public List<BrokerBO> findBySector(Sector sector) {
        List<Broker> broker = brokerRepository.findBySector(sector);
        return broker.stream().map(BrokerMapper::BrokerToBO).toList();
    }

    @Override
    public List<BrokerBO> findAll() {
        List<Broker> broker = brokerRepository.findAll();
        return broker.stream().map(BrokerMapper::BrokerToBO).toList();
    }
}
