package modeling.FsmBroker.configuration;

import modeling.FsmBroker.entity.BrokerageCharges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface BrokerageChargesRepository extends JpaRepository<BrokerageCharges, Long> {

    Optional<BrokerageCharges> findById(long brokerId);
    Optional<BrokerageCharges> findByUuid(String uuid);
    Optional<BrokerageCharges> findByBrokerIdentifier(String brokerIdentifier);
}

