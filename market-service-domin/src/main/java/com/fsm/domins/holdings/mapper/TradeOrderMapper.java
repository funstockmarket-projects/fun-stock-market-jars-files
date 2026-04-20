package com.fsm.domins.holdings.mapper;

import com.fsm.domainsMapping.businessObject.holdingsBO.TradeOrderBO;
import com.fsm.domins.holdings.constants.OrderWindow;
import com.fsm.domins.holdings.model.TradeOrder;

public class TradeOrderMapper {

    public static TradeOrderBO tradeOrderToTradeOrderBO(TradeOrder tradeOrder) {
        if (tradeOrder == null) {
            return null;
        }

        TradeOrderBO bo = new TradeOrderBO();
        bo.setOrderUuid(tradeOrder.orderUuid());
        bo.setOrderId(tradeOrder.orderId());
        bo.setStockName(tradeOrder.stockName());
        bo.setStockSymbol(tradeOrder.stockSymbol());
        bo.setQuantity(tradeOrder.quantity());
        bo.setTradeAt(tradeOrder.tradeAt());
        bo.setTurnOver(tradeOrder.turnOver());
        bo.setTaxAmount(tradeOrder.taxAmount());
        bo.setTransactionDate(tradeOrder.transactionDate());
        bo.setOrderWindow(tradeOrder.orderWindow() != null ?
                com.fsm.domainsMapping.constantsBO.OrderWindowBO.valueOf(tradeOrder.orderWindow().toString()) : null);
        bo.setOrderStatus(tradeOrder.orderStatus());
        bo.setBrokerName(tradeOrder.brokerName());
        bo.setRecordUpdateAt(tradeOrder.recordUpdateAt());
        bo.setRecordStatus(tradeOrder.recordStatus());

        return bo;
    }

    public static TradeOrder tradeOrderBOToTradeOrder(TradeOrderBO tradeOrderBO) {
        if (tradeOrderBO == null) {
            return null;
        }

        return new TradeOrder(
                tradeOrderBO.getOrderUuid(),
                tradeOrderBO.getOrderId(),
                tradeOrderBO.getStockName(),
                tradeOrderBO.getStockSymbol(),
                tradeOrderBO.getQuantity(),
                tradeOrderBO.getTradeAt(),
                tradeOrderBO.getTurnOver(),
                tradeOrderBO.getTaxAmount(),
                tradeOrderBO.getTransactionDate(),
                tradeOrderBO.getOrderWindow() != null ?
                        OrderWindow.valueOf(tradeOrderBO.getOrderWindow().toString()) : null,
                tradeOrderBO.getOrderStatus(),
                tradeOrderBO.getBrokerName(),
                tradeOrderBO.getRecordUpdateAt(),
                tradeOrderBO.getRecordStatus()
        );
    }
}



