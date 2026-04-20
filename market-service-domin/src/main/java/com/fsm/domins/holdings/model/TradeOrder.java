package com.fsm.domins.holdings.model;

import com.fsm.domainsMapping.constantsBO.OrderStatusBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domins.holdings.constants.OrderWindow;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TradeOrder(
        @Id
        UUID orderUuid,
        int orderId,
        String stockName,
        String stockSymbol,
        int quantity,
        BigDecimal tradeAt,
        BigDecimal turnOver,
        BigDecimal taxAmount,
        LocalDate transactionDate,
        OrderWindow orderWindow,
        OrderStatusBO orderStatus,
        String brokerName,
        LocalDate recordUpdateAt,
        RecordStatusBO recordStatus
) {
}
