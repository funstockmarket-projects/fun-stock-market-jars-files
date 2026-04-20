package com.fsm.domainsMapping.businessObject.stockDetailsBO;

import com.fsm.domainsMapping.constantsBO.StockExchangeBO;
import com.fsm.domainsMapping.constantsBO.StockStatusBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDetailsBO {
    private UUID stockUuid;
    private String stockId;
    private String stockSymbol;
    private String stockName;
    private StockExchangeBO exchangeName;
    private StockStatusBO stockStatus;
    private LocalDate updateOrModifiedDateTime;
}

