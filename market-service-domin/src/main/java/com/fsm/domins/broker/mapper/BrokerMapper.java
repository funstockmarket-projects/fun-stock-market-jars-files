package com.fsm.domins.broker.mapper;

import com.fsm.domins.broker.models.BrokerBO;
import com.fsm.domins.broker.models.Broker;

public class BrokerMapper {

    public static Broker bOToBroker(BrokerBO broker){
        return  Broker.builder()
                .brokerUUid(broker.getBrokerUUid())
                .brokerIdentifier(broker.getBrokerIdentifier())
                .brokerName(broker.getBrokerName())
                .NSE_Code(broker.getNSE_Code())
                .BSE_Code(broker.getBSE_Code())
                .SEBI_RegNo(broker.getSEBI_RegNo())
                .depository(broker.getDepository())
                .type(broker.getType())
                .sector(broker.getSector())
                .recordStatus(broker.getRecordStatus())
                .build();
    }
    public static BrokerBO BrokerToBO(Broker brokerBO){
        BrokerBO broker = new BrokerBO();
        broker.setBrokerUUid(brokerBO.brokerUUid());
        broker.setBrokerIdentifier(brokerBO.brokerIdentifier());
        broker.setBrokerName(brokerBO.brokerName());
        broker.setNSE_Code(brokerBO.NSE_Code());
        broker.setBSE_Code(brokerBO.BSE_Code());
        broker.setSEBI_RegNo(brokerBO.SEBI_RegNo());
        broker.setDepository(brokerBO.depository());
        broker.setType(brokerBO.type());
        broker.setSector(brokerBO.sector());
        broker.setRecordStatus(brokerBO.recordStatus());
        return broker;
    }
}
