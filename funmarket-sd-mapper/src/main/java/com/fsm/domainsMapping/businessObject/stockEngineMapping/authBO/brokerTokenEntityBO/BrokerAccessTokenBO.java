package com.fsm.domainsMapping.businessObject.stockEngineMapping.authBO.brokerTokenEntityBO;

import com.fsm.domainsMapping.businessObject.stockEngineMapping.authBO.constantsBO.TokenStatusBO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BrokerAccessTokenBO {
    private Integer tokenId;
    private String brokerName;
    private String accessToken;
    private LocalDate tokenDate;
    private LocalDate tokenExpiryDate;
    private Integer tokenDay;
    private TokenStatusBO tokenStatus;
}
