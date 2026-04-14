package com.fsm.domainsMapping.businessObject.brokerBO;

import com.fsm.domainsMapping.constantsBO.*;
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
    private DepositoryBO depositoryBO;
    private BrokerTypeBO typeBO;
    private SectorBO sectorBO;
    private BrokerStatusBO brokerStatusBO;
    private RecordStatusBO recordStatusBO;
}

