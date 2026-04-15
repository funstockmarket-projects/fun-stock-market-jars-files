package com.fsm.domins.broker.mapper;

import com.fsm.domainsMapping.businessObject.brokerBO.BrokerBO;
import com.fsm.domainsMapping.constantsBO.*;
import com.fsm.domins.broker.constants.BrokerStatus;
import com.fsm.domins.broker.constants.BrokerType;
import com.fsm.domins.broker.constants.Depository;
import com.fsm.domins.broker.constants.Sector;
import com.fsm.domins.broker.models.Broker;
import com.fsm.domins.globalenums.RecordStatus;

public class BrokerMapper {

    public static Broker bOToBroker(BrokerBO broker){
        return  Broker.builder()
                .brokerUUid(broker.getBrokerUUid())
                .brokerIdentifier(broker.getBrokerIdentifier())
                .brokerName(broker.getBrokerName())
                .NSE_Code(broker.getNSE_Code())
                .BSE_Code(broker.getBSE_Code())
                .SEBI_RegNo(broker.getSEBI_RegNo())
                .depository(Depository.valueOf(broker.getDepositoryBO().getDepository()))
                .type(BrokerType.valueOf(broker.getTypeBO().getBrokerType()))
                .sector(Sector.valueOf(broker.getSectorBO().getSectorName()))
                .brokerStatus(BrokerStatus.valueOf(broker.getBrokerStatusBO().getStatus()))
                .recordStatus(RecordStatus.valueOf(broker.getRecordStatusBO().getValue()))
                .build();
    }
    public static BrokerBO BrokerToBO(Broker brokerBO){
        if (brokerBO == null) {
            throw new IllegalArgumentException("Broker cannot be null");
        }
        if (brokerBO.depository() == null) {
            throw new IllegalArgumentException("Depository cannot be null");
        }
        if (brokerBO.type() == null) {
            throw new IllegalArgumentException("BrokerType cannot be null");
        }
        if (brokerBO.sector() == null) {
            throw new IllegalArgumentException("Sector cannot be null");
        }
        if (brokerBO.recordStatus() == null) {
            throw new IllegalArgumentException("RecordStatus cannot be null");
        }
        
        BrokerBO broker = new BrokerBO();
        broker.setBrokerUUid(brokerBO.brokerUUid());
        broker.setBrokerIdentifier(brokerBO.brokerIdentifier());
        broker.setBrokerName(brokerBO.brokerName());
        broker.setNSE_Code(brokerBO.NSE_Code());
        broker.setBSE_Code(brokerBO.BSE_Code());
        broker.setSEBI_RegNo(brokerBO.SEBI_RegNo());
        broker.setDepositoryBO(DepositoryBO.valueOf(brokerBO.depository().getDepository()));
        broker.setTypeBO(BrokerTypeBO.valueOf(brokerBO.type().getBrokerType()));
        broker.setSectorBO(SectorBO.valueOf(brokerBO.sector().getSectorName()));
        broker.setBrokerStatusBO(BrokerStatusBO.valueOf(brokerBO.brokerStatus().getStatus()));
        broker.setRecordStatusBO(RecordStatusBO.valueOf(brokerBO.recordStatus().getValue()));
        return broker;
    }
}
