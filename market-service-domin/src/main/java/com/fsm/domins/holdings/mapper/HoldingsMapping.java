package com.fsm.domins.holdings.mapper;

import com.fsm.domainsMapping.businessObject.holdingsBO.HoldingsBO;
import com.fsm.domainsMapping.businessObject.holdingsBO.TradeOrderBO;
import com.fsm.domainsMapping.constantsBO.OrderCategoryBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domainsMapping.constantsBO.StockExchangeBO;
import com.fsm.domainsMapping.constantsBO.TradingStatusBO;
import com.fsm.domins.globalenums.RecordStatus;
import com.fsm.domins.holdings.constants.OrderCategory;
import com.fsm.domins.holdings.constants.StockExchange;
import com.fsm.domins.holdings.constants.TradingStatus;
import com.fsm.domins.holdings.model.Holdings;
import com.fsm.domins.holdings.model.TradeOrder;

import java.util.List;
import java.util.stream.Collectors;

public class HoldingsMapping {

    public static HoldingsBO HoldingsToBO(Holdings holdings) {

        HoldingsBO holdingsBO = new HoldingsBO();
        holdingsBO.setHoldingsUuid(holdings.holdingsUuid());
        holdingsBO.setFunMarketAccountId(holdings.funMarketAccountId());
        holdingsBO.setStockName(holdings.stockName());
        holdingsBO.setStockSymbol(holdings.stockSymbol());
        holdingsBO.setBrokerName(holdings.brokerName());
        holdingsBO.setQuantity(holdings.quantity());

        // Convert TradeOrder lists to TradeOrderBO lists
        List<TradeOrderBO> buyOrderBOs = holdings.buyOrder() != null ?
                holdings.buyOrder().stream()
                        .map(TradeOrderMapper::tradeOrderToTradeOrderBO)
                        .collect(Collectors.toList()) : null;
        List<TradeOrderBO> sellOrderBOs = holdings.sellOrder() != null ?
                holdings.sellOrder().stream()
                        .map(TradeOrderMapper::tradeOrderToTradeOrderBO)
                        .collect(Collectors.toList()) : null;

        holdingsBO.setBuyOrder(buyOrderBOs);
        holdingsBO.setSellOrder(sellOrderBOs);
        holdingsBO.setCurrencyValue(holdings.currencyValue());
        holdingsBO.setAverageCostPerShare(holdings.averageCostPerShare());
        holdingsBO.setTotalTaxAmount(holdings.totalTaxAmount());
        holdingsBO.setStockExchange(holdings.stockExchange() != null ?
                StockExchangeBO.valueOf(holdings.stockExchange().toString()) : null);
        holdingsBO.setTradingStatus(holdings.tradingStatus() != null ?
                TradingStatusBO.valueOf(holdings.tradingStatus().toString()) : null);
        holdingsBO.setOrderCategory(holdings.orderCategory() != null ?
                OrderCategoryBO.valueOf(holdings.orderCategory().toString()) : null);
        holdingsBO.setRecordUpdateAt(holdings.recordUpdateAt());
        holdingsBO.setRecordStatus(RecordStatusBO.valueOf(holdings.recordStatus().getValue()));

        return holdingsBO;
    }

    public static Holdings boToHoldings(HoldingsBO holdingsBO) {

        // Convert TradeOrderBO lists to TradeOrder lists
        List<TradeOrder> buyOrders = holdingsBO.getBuyOrder() != null ?
                holdingsBO.getBuyOrder().stream()
                        .map(TradeOrderMapper::tradeOrderBOToTradeOrder)
                        .collect(Collectors.toList()) : null;
        List<TradeOrder> sellOrders = holdingsBO.getSellOrder() != null ?
                holdingsBO.getSellOrder().stream()
                        .map(TradeOrderMapper::tradeOrderBOToTradeOrder)
                        .collect(Collectors.toList()) : null;

        return new Holdings(
                holdingsBO.getHoldingsUuid(),
                holdingsBO.getFunMarketAccountId(),
                holdingsBO.getStockName(),
                holdingsBO.getStockSymbol(),
                holdingsBO.getBrokerName(),
                holdingsBO.getQuantity(),
                buyOrders,
                sellOrders,
                holdingsBO.getCurrencyValue(),
                holdingsBO.getAverageCostPerShare(),
                holdingsBO.getTotalTaxAmount(),
                holdingsBO.getStockExchange() != null ?
                        StockExchange.valueOf(holdingsBO.getStockExchange().toString()) : null,
                holdingsBO.getTradingStatus() != null ?
                        TradingStatus.valueOf(holdingsBO.getTradingStatus().toString()) : null,
                holdingsBO.getOrderCategory() != null ?
                        OrderCategory.valueOf(holdingsBO.getOrderCategory().toString()) : null,
                holdingsBO.getRecordUpdateAt(),
                holdingsBO.getRecordStatus() != null ?
                        RecordStatus.valueOf(holdingsBO.getRecordStatus().getValue()) :
                        RecordStatus.ADDED
        );
    }
}
