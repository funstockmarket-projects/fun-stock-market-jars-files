package com.fsm.domins.broker.mapper;

import com.fsm.domins.broker.constants.BrokerType;
import com.fsm.domins.broker.constants.Depository;
import com.fsm.domins.broker.constants.Sector;
import com.fsm.domins.broker.models.Broker;
import com.fsm.domins.globalenums.RecordStatus;
import com.fsm.dominsMapping.businessObject.brokerBO.BrokerBO;
import com.fsm.dominsMapping.constantsBO.*;

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
                .recordStatus(RecordStatus.valueOf(broker.getRecordStatusBO().getValue()))
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
        broker.setDepositoryBO(DepositoryBO.valueOf(brokerBO.depository().getDepository()));
        broker.setTypeBO(BrokerTypeBO.valueOf(brokerBO.type().getBrokerType()));
        broker.setSectorBO(SectorBO.valueOf(brokerBO.sector().getSectorName()));
        broker.setRecordStatusBO(RecordStatusBO.valueOf(brokerBO.recordStatus().getValue()));
        return broker;
    }
}
