package com.fsm.domainsMapping.businessObject.holdingsBO;

import com.fsm.domainsMapping.constantsBO.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeOrderBO {
    
    private UUID orderUuid;
    private int orderId;
    private String stockName;
    private String stockSymbol;
    private int quantity;
    private BigDecimal tradeAt;
    private BigDecimal turnOver;
    private BigDecimal taxAmount;
    private LocalDate transactionDate;
    private OrderWindowBO orderWindow;
    private OrderStatusBO orderStatus;
    private String brokerName;
    private LocalDate recordUpdateAt;
    private RecordStatusBO recordStatus;
}