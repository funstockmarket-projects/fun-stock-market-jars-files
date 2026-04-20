package com.fsm.domins.stockDetails.models;

import com.fsm.domins.holdings.constants.StockExchange;
import com.fsm.domins.stockDetails.constaunts.StockStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document(collation = "stockDetails")
public record StockDetails(
        @Id
        UUID stockUuid,
        String stockId,
        String stockSymbol,
        String stockName,
        StockExchange exchangeName,
        StockStatus stockStatus,
        LocalDate updateOrModifiedDateTime
) {
}
