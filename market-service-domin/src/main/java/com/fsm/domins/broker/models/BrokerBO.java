package com.fsm.domins.broker.models;


import com.fsm.domins.broker.constants.BrokerType;
import com.fsm.domins.broker.constants.Depository;
import com.fsm.domins.broker.constants.Sector;
import com.fsm.domins.globalenums.RecordStatus;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrokerBO {

    private String brokerUUid;
    private String brokerIdentifier;
    private String brokerName;
    private String NSE_Code;
    private String BSE_Code;
    private String SEBI_RegNo;
    private Depository depository;
    private BrokerType type;
    private Sector sector;
    private RecordStatus recordStatus;
}
