package com.fsm.domins.broker.models;

import com.fsm.domins.broker.constants.BrokerType;
import com.fsm.domins.broker.constants.Depository;
import com.fsm.domins.broker.constants.Sector;
import com.fsm.domins.globalenums.RecordStatus;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="broker")
@Builder
public record Broker(String brokerUUid,
                     String brokerIdentifier,
                     String brokerName,
                     String NSE_Code,
                     String BSE_Code,
                     String SEBI_RegNo,
                     Depository depository,
                     BrokerType type,
                     Sector sector,
                     RecordStatus recordStatus) {}