package modeling.FsmBroker.configuration;

import modeling.FsmBroker.entity.Broker;
import modeling.globalEnums.PerformanceStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface BrokerRepository extends JpaRepository<Broker, Long> {

    Optional<Broker> findById(long id);
    Optional<Broker> findByBrokerUuid(String brokerUuid);
    Optional<Broker> findByBrokerIdentifier(String brokerIdentifier);
    Optional<Broker> findByBrokerName(String brokerName);
    List<Broker> findByBrokerStatus(PerformanceStatus brokerStatus);
}

