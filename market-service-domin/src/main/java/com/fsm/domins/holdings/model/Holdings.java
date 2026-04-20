package com.fsm.domins.holdings.model;

import com.fsm.domins.globalenums.RecordStatus;
import com.fsm.domins.holdings.constants.OrderCategory;
import com.fsm.domins.holdings.constants.StockExchange;
import com.fsm.domins.holdings.constants.TradingStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document(collection = "holdings")
public record Holdings(
        @Id
        UUID holdingsUuid,
        String funMarketAccountId,
        String stockName,
        String stockSymbol,
        String brokerName,
        int quantity,
        List<TradeOrder>buyOrder,
        List<TradeOrder>sellOrder,
        String currencyValue,
        BigDecimal averageCostPerShare,
        BigDecimal totalTaxAmount,
        StockExchange stockExchange,
        TradingStatus tradingStatus,
        OrderCategory orderCategory,
        List<LocalDate>recordUpdateAt,
        RecordStatus recordStatus) {
}
