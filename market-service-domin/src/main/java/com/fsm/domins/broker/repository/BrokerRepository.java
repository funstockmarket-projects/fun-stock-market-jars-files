package com.fsm.domins.broker.repository;

import com.fsm.domins.broker.constants.BrokerType;
import com.fsm.domins.broker.constants.Depository;
import com.fsm.domins.broker.constants.Sector;
import com.fsm.domins.broker.models.Broker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "BrokerRepository")
public interface BrokerRepository extends MongoRepository<Broker, String> {

    Optional<Broker> findByBrokerUUid(String brokerName);

    Optional<Broker> findByBrokerIdentifier(String brokerIdentifier);

    List<Broker> findByDepository(Depository depository);

    List<Broker> findByType(BrokerType brokerType);

    List<Broker> findBySector(Sector sector);

    void deleteByBrokerIdentifier(String Name);
}
