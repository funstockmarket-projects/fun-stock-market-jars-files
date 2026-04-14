package com.fsm.domins.broker.broketOperations;

import com.fsm.domainsMapping.businessObject.brokerBO.BrokerBO;
import com.fsm.domins.broker.constants.BrokerType;
import com.fsm.domins.broker.constants.Depository;
import com.fsm.domins.broker.constants.Sector;

import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "FunMarketBrokerRetrievalMethods" )
public sealed interface FunMarketBrokerRetrievalMethods permits FunMarketBrokerRetrievals {

    BrokerBO findByBrokerUUid(String brokerName);

    BrokerBO findByBrokerIdentifier(String brokerIdentifier);

    List<BrokerBO> findByDepository(Depository depository);

    List<BrokerBO> findByType(BrokerType brokerType);

    List<BrokerBO> findBySector(Sector sector);

    List<BrokerBO> findAll();
}
