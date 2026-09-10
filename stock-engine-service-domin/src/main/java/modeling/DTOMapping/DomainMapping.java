package modeling.DTOMapping;

import modeling.FsmBroker.DTO.BrokerDTO;
import modeling.FsmBroker.DTO.BrokerageChargesDTO;
import modeling.FsmBroker.entity.Broker;
import modeling.FsmBroker.entity.BrokerageCharges;
import modeling.FsmHoldings.DTO.HoldingsDTO;
import modeling.FsmHoldings.enitity.UserHoldings;
import modeling.FsmStockExchange.DTO.StockExchangeDTO;
import modeling.FsmStockExchange.entity.StockExchange;
import modeling.fsmUsers.DTO.FSM_UserDetailsDTO;
import modeling.fsmUsers.userEntity.FSM_Users;
import modeling.trade.DTO.ClearingDTO;
import modeling.trade.DTO.TradeOrderDTO;
import modeling.trade.entity.ClearingTradeOrder;
import modeling.trade.entity.TradeOrder;

public class DomainMapping {
    public static FSM_UserDetailsDTO userMapping(FSM_Users re) {
        return FSM_UserDetailsDTO.builder()
                .id(re.getId())
                .userUuid(re.getUserUuid())
                .funmarketPolicyId(re.getFunmarketPolicyId())
                .userName(re.getUserName())
                .email(re.getEmail())
                .mobileNumber(re.getMobileNumber())
                .userStatus(re.getUserStatus())
                .userType(re.getUserType())
                .accountType(re.getAccountType())
                .userActiveStatusCode(re.getUserActiveStatusCode())
                .accountOpeningTime(re.getAccountOpeningTime())
                .accountClosingDateTime(re.getAccountClosingDateTime())
                .recordCreatedOrModifiedDateTime(re.getRecordCreatedOrModifiedDateTime())
                .recordStatus(re.getRecordStatus())
                .build();
    }

    public static HoldingsDTO holdingsMapping(UserHoldings holdings) {
        return HoldingsDTO.builder()
                .holdingUUID(holdings.getHoldingUUID())
                .userName(holdings.getUserName())
                .typeOfHoldingsAccount(holdings.getTypeOfHoldingsAccount())
                .totalStockHoldings(holdings.getTotalStockHoldings())
                .currentValue(holdings.getCurrentValue())
                .totalInvestment(holdings.getTotalInvestment())
                .holdingsOpeningDateAndTime(holdings.getHoldingsOpeningDateAndTime())
                .accountStatus(holdings.getAccountStatus()) // maps to PerformanceStatus
                .recordCreatedOrModifiedDateTime(holdings.getRecordCreatedOrModifiedDateTime())
                .recordStatus(holdings.getRecordStatus())
                .users(userMapping(holdings.getUsers())) // convert FSM_Users → FSM_UserDetailsDTO
                .build();
    }

    public static StockExchangeDTO stockExchangeMapping(StockExchange exchange) {
        if (exchange == null) {
            return null;
        }
        return StockExchangeDTO.builder()
                .id(exchange.getId())
                .exchangeUuid(exchange.getExchangeUuid())
                .exchangeName(exchange.getExchangeName())
                .exchangeStatus(exchange.getExchangeStatus())
                .recordStatus(exchange.getRecordStatus())
                .recordCreatedOrModifiedDateTime(exchange.getRecordCreatedOrModifiedDateTime())
                .build();
    }

    public static StockExchange stockExchangeMapping(StockExchangeDTO exchange) {
       if (exchange == null) {
            return null;
        }
        return StockExchange.builder()
                .id(exchange.getId())
                .exchangeUuid(exchange.getExchangeUuid())
                .exchangeName(exchange.getExchangeName())
                .exchangeStatus(exchange.getExchangeStatus())
                .recordStatus(exchange.getRecordStatus())
                .recordCreatedOrModifiedDateTime(exchange.getRecordCreatedOrModifiedDateTime())
                .build();
    }

    public static BrokerDTO brokerMapping(Broker broker) {
        if (broker == null) {
            return null;
        }
        return BrokerDTO.builder()
                .id(broker.getId())
                .brokerUuid(broker.getBrokerUuid())
                .brokerIdentifier(broker.getBrokerIdentifier())
                .brokerName(broker.getBrokerName())
                .nseCode(broker.getNseCode())
                .bseCode(broker.getBseCode())
                .sebiRegNo(broker.getSebiRegNo())
                .depository(broker.getDepository())
                .brokerType(broker.getBrokerType())
                .sector(broker.getSector())
                .brokerStatus(broker.getBrokerStatus())
                .recordStatus(broker.getRecordStatus())
                .recordCreatedOrModifiedDateTime(broker.getRecordCreatedOrModifiedDateTime())
                .brokerageCharges(brokerageChargesMapping(broker.getBrokerageCharges()))
                .build();
    }

    public static Broker brokerMapping(BrokerDTO dto) {
    if (dto == null) {
        return null;
    }
    Broker broker = Broker.builder()
            .id(dto.getId())
            .brokerUuid(dto.getBrokerUuid())
            .brokerIdentifier(dto.getBrokerIdentifier())
            .brokerName(dto.getBrokerName())
            .nseCode(dto.getNseCode())
            .bseCode(dto.getBseCode())
            .sebiRegNo(dto.getSebiRegNo())
            .depository(dto.getDepository())
            .brokerType(dto.getBrokerType())
            .sector(dto.getSector())
            .brokerStatus(dto.getBrokerStatus())
            .recordStatus(dto.getRecordStatus())
            .recordCreatedOrModifiedDateTime(dto.getRecordCreatedOrModifiedDateTime())
            .build();

    BrokerageCharges charges = brokerageChargesMapping(dto.getBrokerageCharges());
    if (charges != null) {
        charges.setBroker(broker); // critical for @MapsId
        broker.setBrokerageCharges(charges);
    }

    return broker;
}


    public static BrokerageChargesDTO brokerageChargesMapping(BrokerageCharges charges) {
        if (charges == null) {
            return null;
        }
        return BrokerageChargesDTO.builder()
                .brokerId(charges.getBrokerId())
                .uuid(charges.getUuid())
                .brokerIdentifier(charges.getBrokerIdentifier())
                .equityDeliveryMin(charges.getEquityDeliveryMin())
                .equityDeliveryMax(charges.getEquityDeliveryMax())
                .equityIntradayMin(charges.getEquityIntradayMin())
                .equityIntradayMax(charges.getEquityIntradayMax())
                .equityFuturesMin(charges.getEquityFuturesMin())
                .equityFuturesMax(charges.getEquityFuturesMax())
                .equityOptions(charges.getEquityOptions())
                .dp(charges.getDp())
                .recordStatus(charges.getRecordStatus())
                .recordCreatedOrModifiedDateTime(charges.getRecordCreatedOrModifiedDateTime())
                .build();
    }

    public static BrokerageCharges brokerageChargesMapping(BrokerageChargesDTO dto) {
        if (dto == null) {
            return null;
        }
        return BrokerageCharges.builder()
                .brokerId(dto.getBrokerId())
                .uuid(dto.getUuid())
                .brokerIdentifier(dto.getBrokerIdentifier())
                .equityDeliveryMin(dto.getEquityDeliveryMin())
                .equityDeliveryMax(dto.getEquityDeliveryMax())
                .equityIntradayMin(dto.getEquityIntradayMin())
                .equityIntradayMax(dto.getEquityIntradayMax())
                .equityFuturesMin(dto.getEquityFuturesMin())
                .equityFuturesMax(dto.getEquityFuturesMax())
                .equityOptions(dto.getEquityOptions())
                .dp(dto.getDp())
                .recordStatus(dto.getRecordStatus())
                .recordCreatedOrModifiedDateTime(dto.getRecordCreatedOrModifiedDateTime())
                .build();
    }

    public static TradeOrder toEntity(TradeOrderDTO dto) {
        if (dto == null) {
            return null;
        }

        TradeOrder entity = new TradeOrder();
        entity.setOrderId(dto.getOrderId());
        entity.setOrderUuid(dto.getOrderUuid());
        entity.setUserId(dto.getUserId());
        entity.setStockBucketIdInFsm(dto.getStockBucketIdInFsm());
        entity.setStockSymbol(dto.getStockSymbol());
        entity.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0L);
        entity.setTradeAt(dto.getTradeAt());
        entity.setBrokerName(dto.getBrokerName());
        entity.setOrderWindow(dto.getOrderWindow());
        entity.setTradeOrderType(dto.getTradeOrderType());
        entity.setTradeClearingProcessDone(dto.getTradeClearingProcessDone());
        entity.setTradeOrderBusinessDateTime(dto.getTradeOrderBusinessDateTime());
        entity.setTradeOrderDateInBrokerAccount(dto.getTradeOrderDateInBrokerAccount());
        entity.setFsmTradeOrderRegesterDateTime(dto.getFsmTradeOrderRegesterDateTime());
        entity.setTradeOrderStatusInOriginalBrokerAccount(dto.getTradeOrderStatusInOriginalBrokerAccount());
        entity.setTradeOrderStatusInFsmAccount(dto.getTradeOrderStatusInFsmAccount());
        entity.setStockDetailsAvalibilityInFsm(dto.getStockDetailsAvalibilityInFsm());
        entity.setStockApprovedStatusInFsm(dto.getStockApprovedStatusInFsm());
        entity.setFsmStockDescription(dto.getFsmStockDescription());
        entity.setRecordStatus(dto.getRecordStatus());
        entity.setExchangeName(dto.getExchangeName());

        return entity;
    }

    public static TradeOrderDTO toDto(TradeOrder entity) {
        if (entity == null) {
            return null;
        }

        TradeOrderDTO dto = new TradeOrderDTO();
        dto.setOrderId(entity.getOrderId());
        dto.setOrderUuid(entity.getOrderUuid());
        dto.setUserId(entity.getUserId());
        dto.setStockBucketIdInFsm(entity.getStockBucketIdInFsm());
        dto.setStockSymbol(entity.getStockSymbol());
        dto.setQuantity(entity.getQuantity());
        dto.setTradeAt(entity.getTradeAt());
        dto.setBrokerName(entity.getBrokerName());
        dto.setOrderWindow(entity.getOrderWindow());
        dto.setTradeOrderType(entity.getTradeOrderType());
        dto.setTradeClearingProcessDone(entity.getTradeClearingProcessDone());
        dto.setTradeOrderBusinessDateTime(entity.getTradeOrderBusinessDateTime());
        dto.setTradeOrderDateInBrokerAccount(entity.getTradeOrderDateInBrokerAccount());
        dto.setFsmTradeOrderRegesterDateTime(entity.getFsmTradeOrderRegesterDateTime());
        dto.setTradeOrderStatusInOriginalBrokerAccount(entity.getTradeOrderStatusInOriginalBrokerAccount());
        dto.setTradeOrderStatusInFsmAccount(entity.getTradeOrderStatusInFsmAccount());
        dto.setStockDetailsAvalibilityInFsm(entity.getStockDetailsAvalibilityInFsm());
        dto.setStockApprovedStatusInFsm(entity.getStockApprovedStatusInFsm());
        dto.setFsmStockDescription(entity.getFsmStockDescription());
        dto.setRecordStatus(entity.getRecordStatus());
        dto.setRecordCreatedOrModifiedDateTime(entity.getRecordCreatedOrModifiedDateTime());
        dto.setExchangeName(entity.getExchangeName());

        if(entity.getFsmUsers() != null) {
            dto.setFsmUsers(userMapping(entity.getFsmUsers()));
        }
        if(entity.getStockExchange() != null) {
            dto.setStockExchange(stockExchangeMapping(entity.getStockExchange()));
        }
        if(entity.getBroker() != null) {
            dto.setStockBrokerDTO(brokerMapping(entity.getBroker()));
        }
        if(entity.getClearingTradeOrder() != null) {
            dto.setClearingTradeOrder(toDto(entity.getClearingTradeOrder()));
        }

        return dto;
    }

    public static ClearingTradeOrder toEntity(ClearingDTO dto) {
        if (dto == null) {
            return null;
        }

        ClearingTradeOrder entity = new ClearingTradeOrder();
        entity.setClearingId(dto.getClearingId());
        entity.setClearingUuid(dto.getClearingUuid());
        entity.setAccountId(dto.getAccountId());
        entity.setClearingStatusCode(dto.getClearingStatusCode());
        entity.setClearingMessage(dto.getClearingMessage());
        entity.setIsRejectedTrade(dto.getIsRejectedTrade());
        entity.setRecordStatus(dto.getRecordStatus());

        return entity;
    }

    public static ClearingDTO toDto(ClearingTradeOrder entity) {
        if (entity == null) {
            return null;
        }

        ClearingDTO dto = new ClearingDTO();
        dto.setClearingId(entity.getClearingId());
        dto.setClearingUuid(entity.getClearingUuid());
        dto.setAccountId(entity.getAccountId());
        dto.setClearingStatusCode(entity.getClearingStatusCode());
        dto.setClearingMessage(entity.getClearingMessage());
        dto.setIsRejectedTrade(entity.getIsRejectedTrade());
        dto.setRecordStatus(entity.getRecordStatus());
        dto.setRecordCreatedOrModifiedDateTime(entity.getRecordCreatedOrModifiedDateTime());

        return dto;
    }
}
