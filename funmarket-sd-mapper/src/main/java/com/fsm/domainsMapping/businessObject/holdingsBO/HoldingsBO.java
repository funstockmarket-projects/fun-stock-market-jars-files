package com.fsm.domainsMapping.businessObject.holdingsBO;

import com.fsm.domainsMapping.constantsBO.OrderCategoryBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domainsMapping.constantsBO.StockExchangeBO;
import com.fsm.domainsMapping.constantsBO.TradingStatusBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoldingsBO {

    private UUID holdingsUuid;
    private String funMarketAccountId;
    private String stockName;
    private String stockSymbol;
    private String brokerName;
    private int quantity;
    private List<TradeOrderBO> buyOrder;
    private List<TradeOrderBO> sellOrder;
    private String currencyValue;
    private BigDecimal averageCostPerShare;
    private BigDecimal totalTaxAmount;
    private StockExchangeBO stockExchange;
    private TradingStatusBO tradingStatus;
    private OrderCategoryBO orderCategory;
    private List<LocalDate> recordUpdateAt;
    private RecordStatusBO recordStatus;
}