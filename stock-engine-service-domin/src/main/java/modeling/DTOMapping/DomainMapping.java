package modeling.DTOMapping;

import modeling.FsmBroker.DTO.BrokerDTO;
import modeling.FsmBroker.DTO.BrokerageChargesDTO;
import modeling.FsmBroker.entity.Broker;
import modeling.FsmBroker.entity.BrokerageCharges;
import modeling.FsmHoldings.DTO.HoldingsDTO;
import modeling.FsmHoldings.enitity.UserHoldings;
import modeling.FsmStockExchange.DTO.StockExchangeDTO;
import modeling.FsmStockExchange.entity.StockExchange;
import modeling.dailyStockTradingBucket.DTO.DailyStockTriggeringDTO;
import modeling.dailyStockTradingBucket.DTO.FsmStocksBucketDTO;
import modeling.dailyStockTradingBucket.DTO.TrackStockPriceActionDTO;
import modeling.dailyStockTradingBucket.entities.DailyStockTriggering;
import modeling.dailyStockTradingBucket.entities.FsmStocksBucket;
import modeling.dailyStockTradingBucket.entities.TrackStockPriceAction;
import modeling.fsmBuckets.DTO.FSM_StockDetailsBucketDTO;
import modeling.fsmBuckets.entities.FSM_StockDetailsBucket;
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
// 
        TradeOrder entity = new TradeOrder();
        entity.setOrderId(dto.getOrderId());
        entity.setOrginalTradeOrderUUID(dto.getOrginalTradeOrderUUID());
        entity.setOrderUuid(dto.getOrderUuid());
        entity.setUserId(dto.getUserId());
        entity.setStockBucketIdInFsm(dto.getStockBucketIdInFsm());
        entity.setStockSymbol(dto.getStockSymbol());
        entity.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : 0L);
        entity.setTradeAt(dto.getTradeAt());
        entity.setBrokerId(dto.getBrokerId());
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
        entity.setExchangeId(dto.getExchangeId());
        entity.setClearingBusinessDate(dto.getClearingBusinessDate());
        if(dto.getClearingTradeOrderDTO() != null) {

            entity.setClearingTradeOrder(
                dto.getClearingTradeOrderDTO()
                .stream()
                .map(i->{
                    ClearingTradeOrder clearingTradeOrder =  toEntity(i);
                    clearingTradeOrder.setTradeOrder(entity);
                    return clearingTradeOrder;
                })
                .toList()
            );
        }else{
            throw new NullPointerException("Cannot map the clearing.. class mapping");
        }
        return entity;
    }

    public static TradeOrderDTO toDto(TradeOrder entity) {
        if (entity == null) {
            return null;
        }

        TradeOrderDTO dto = new TradeOrderDTO();
        dto.setClearingBusinessDate(entity.getClearingBusinessDate());
        dto.setOrderId(entity.getOrderId());
        dto.setOrginalTradeOrderUUID(entity.getOrginalTradeOrderUUID());
        dto.setOrderUuid(entity.getOrderUuid());
        dto.setUserId(entity.getUserId());
        dto.setStockBucketIdInFsm(entity.getStockBucketIdInFsm());
        dto.setStockSymbol(entity.getStockSymbol());
        dto.setQuantity(entity.getQuantity());
        dto.setTradeAt(entity.getTradeAt());
        dto.setBrokerId(entity.getBrokerId());
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
        dto.setExchangeId(entity.getExchangeId());

        if(entity.getFsmUsers() != null) {
            dto.setFsmUsersDTO(userMapping(entity.getFsmUsers()));
        }
        if(entity.getStockExchange() != null) {
            dto.setStockExchangeDTO(stockExchangeMapping(entity.getStockExchange()));
        }
        if(entity.getBroker() != null) {
            dto.setStockBrokerDTO(brokerMapping(entity.getBroker()));
        }
        if(entity.getClearingTradeOrder() != null) {
            dto.setClearingTradeOrderDTO(
                
                entity.getClearingTradeOrder()
                .stream()
                .map(n->toDto(n))
                .toList()
            );
        }

        return dto;
    }
    public static TradeOrderDTO treadOrderDTOWithOutEntitys(TradeOrder entity) {
        if (entity == null) {
            return null;
        }

        TradeOrderDTO dto = new TradeOrderDTO();
        dto.setClearingBusinessDate(entity.getClearingBusinessDate());
        dto.setOrderId(entity.getOrderId());
        dto.setOrginalTradeOrderUUID(entity.getOrginalTradeOrderUUID());
        dto.setOrderUuid(entity.getOrderUuid());
        dto.setUserId(entity.getUserId());
        dto.setStockBucketIdInFsm(entity.getStockBucketIdInFsm());
        dto.setStockSymbol(entity.getStockSymbol());
        dto.setQuantity(entity.getQuantity());
        dto.setTradeAt(entity.getTradeAt());
        dto.setBrokerId(entity.getBrokerId());
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
        dto.setExchangeId(entity.getExchangeId());

        if(entity.getClearingTradeOrder() != null) {
            dto.setClearingTradeOrderDTO(
                
                entity.getClearingTradeOrder()
                .stream()
                .map(n->toDto(n))
                .toList()
            );
        }

        return dto;
    }

    public static ClearingTradeOrder toEntity(ClearingDTO dto) {
        if (dto == null) {
            throw new NullPointerException("Cannot be null dto class mapping->toentity");
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

    public static FSM_StockDetailsBucketDTO stockDetailsBucketMapping(FSM_StockDetailsBucket entity) {
        if (entity == null) {
            return null;
        }
        return FSM_StockDetailsBucketDTO.builder()
                .bucketId(entity.getBucketId())
                .instrument_key(entity.getInstrument_key())
                .bucketUuid(entity.getBucketUuid())
                .stockSymbol(entity.getStockSymbol())
                .stockName(entity.getStockName())
                .exchangeName(entity.getExchangeName())
                .fsmStockIdentification(entity.getFsmStockIdentification())
                .stockAddedThroughUserId(entity.getStockAddedThroughUserId())
                .stockRegisterWithTradeOrderId(entity.getStockRegisterWithTradeOrderId())
                .isStockFoundInGlobalMarket(entity.getIsStockFoundInGlobalMarket())
                .listedDateInFsm(entity.getListedDateInFsm())
                .listedDayInFsm(entity.getListedDayInFsm())
                .fsmStockProcessedDateTime(entity.getFsmStockProcessedDateTime())
                .fsmStockProcessedDay(entity.getFsmStockProcessedDay())
                .stockTradingStartTime(entity.getStockTradingStartTime())
                .stockTradingEndTime(entity.getStockTradingEndTime())
                .capType(entity.getCapType())
                .processingStatus(entity.getProcessingStatus())
                .isStockStatusInMarket(entity.getIsStockStatusInMarket())
                .stockStatusInFsm(entity.getStockStatusInFsm())
                .isStockDelistedMarket(entity.getIsStockDelistedMarket())
                .tradingStatus(entity.getTradingStatus())
                .stockDetailsAvalibilityFromBroker(entity.getStockDetailsAvalibilityFromBroker())
                .stockApprovalStatus(entity.getStockApprovalStatus())
                .stockApprovedByAdminId(entity.getStockApprovedByAdminId())
                .stockApprovedDateTime(entity.getStockApprovedDateTime())
                .stockRejectedByAdminId(entity.getStockRejectedByAdminId())
                .stockRejectedDateTime(entity.getStockRejectedDateTime())
                .stockRejectionReason(entity.getStockRejectionReason())
                .recordCreationOrModifiedDateTime(entity.getRecordCreationOrModifiedDateTime())
                .recordStatus(entity.getRecordStatus())
                .fsmUsers(entity.getFsmUsers() != null ? userMapping(entity.getFsmUsers()) : null)
                .stockApprovalAdminDetails(entity.getStockApprovalAdminDetails() != null ? userMapping(entity.getStockApprovalAdminDetails()) : null)
                .stockRejectionAdminDetails(entity.getStockRejectionAdminDetails() != null ? userMapping(entity.getStockRejectionAdminDetails()) : null)
                .tradeOrder(entity.getTradeOrder() != null ? treadOrderDTOWithOutEntitys(entity.getTradeOrder()) : null)
                .stockExchange(entity.getStockExchange() != null ? stockExchangeMapping(entity.getStockExchange()) : null)
                .build();
    }

    public static FSM_StockDetailsBucket stockDetailsBucketMapping(FSM_StockDetailsBucketDTO dto) {
        if (dto == null) {
            return null;
        }
        FSM_StockDetailsBucket entity = new FSM_StockDetailsBucket();
        entity.setBucketId(dto.getBucketId());
        entity.setInstrument_key(dto.getInstrument_key());
        entity.setBucketUuid(dto.getBucketUuid());
        entity.setStockSymbol(dto.getStockSymbol());
        entity.setStockName(dto.getStockName());
        entity.setExchangeName(dto.getExchangeName());
        entity.setStockAddedThroughUserId(dto.getStockAddedThroughUserId());
        entity.setStockRegisterWithTradeOrderId(dto.getStockRegisterWithTradeOrderId());
        entity.setIsStockFoundInGlobalMarket(dto.getIsStockFoundInGlobalMarket());
        entity.setListedDateInFsm(dto.getListedDateInFsm());
        entity.setListedDayInFsm(dto.getListedDayInFsm());
        entity.setStockTradingStartTime(dto.getStockTradingStartTime());
        entity.setStockTradingEndTime(dto.getStockTradingEndTime());
        entity.setCapType(dto.getCapType());
        entity.setProcessingStatus(dto.getProcessingStatus());
        entity.setIsStockStatusInMarket(dto.getIsStockStatusInMarket());
        entity.setStockStatusInFsm(dto.getStockStatusInFsm());
        entity.setIsStockDelistedMarket(dto.getIsStockDelistedMarket());
        entity.setTradingStatus(dto.getTradingStatus());
        entity.setStockDetailsAvalibilityFromBroker(dto.getStockDetailsAvalibilityFromBroker());
        entity.setStockApprovalStatus(dto.getStockApprovalStatus());
        entity.setStockApprovedByAdminId(dto.getStockApprovedByAdminId());
        entity.setStockApprovedDateTime(dto.getStockApprovedDateTime());
        entity.setStockRejectedByAdminId(dto.getStockRejectedByAdminId());
        entity.setStockRejectedDateTime(dto.getStockRejectedDateTime());
        entity.setStockRejectionReason(dto.getStockRejectionReason());
        return entity;
    }

    public static FsmStocksBucketDTO fsmStocksBucketMapping(FsmStocksBucket e) {
        if (e == null) return null;
        return FsmStocksBucketDTO.builder()
                .stockUuid(e.getStockUuid())
                .stockId(e.getStockId())
                .stockSymbol(e.getStockSymbol())
                .exchangeName(e.getExchangeName())
                .stockStatus(e.getStockStatus())
                .stockTreadingStartTime(e.getStockTreadingStartTime())
                .stockTreadingEndTime(e.getStockTreadingEndTime())
                .currentTradedDate(e.getCurrentTradedDate())
                .currentTradedDay(e.getCurrentTradedDay())
                .lastTradedDate(e.getLastTradedDate())
                .lastTradedDay(e.getLastTradedDay())
                .lastTreadingDayPriceStatus(e.getLastTreadingDayPriceStatus())
                .currentTreadinfDayPriceStatus(e.getCurrentTreadinfDayPriceStatus())
                .recordStatus(e.getRecordStatus())
                .recordCreatedOrModifiedDateTime(e.getRecordCreatedOrModifiedDateTime())
                .stockDetailsBucket(e.getStockDetailsBucket() != null ? stockDetailsBucketMapping(e.getStockDetailsBucket()) : null)
                .stockExchange(e.getStockExchange() != null ? stockExchangeMapping(e.getStockExchange()) : null)
                .build();
    }

    public static FsmStocksBucket fsmStocksBucketMapping(FsmStocksBucketDTO d) {
        if (d == null) return null;
        FsmStocksBucket e = new FsmStocksBucket();
        e.setStockUuid(d.getStockUuid());
        e.setStockId(d.getStockId());
        e.setStockSymbol(d.getStockSymbol());
        e.setExchangeName(d.getExchangeName());
        e.setStockStatus(d.getStockStatus());
        e.setStockTreadingStartTime(d.getStockTreadingStartTime());
        e.setStockTreadingEndTime(d.getStockTreadingEndTime());
        e.setCurrentTradedDate(d.getCurrentTradedDate());
        e.setCurrentTradedDay(d.getCurrentTradedDay());
        e.setLastTradedDate(d.getLastTradedDate());
        e.setLastTradedDay(d.getLastTradedDay());
        e.setLastTreadingDayPriceStatus(d.getLastTreadingDayPriceStatus());
        e.setCurrentTreadinfDayPriceStatus(d.getCurrentTreadinfDayPriceStatus());
        e.setRecordStatus(d.getRecordStatus());
        return e;
    }

    public static DailyStockTriggeringDTO dailyStockTriggeringMapping(DailyStockTriggering e) {
        if (e == null) return null;
        return DailyStockTriggeringDTO.builder()
                .stockPriceUpdatedUUID(e.getStockPriceUpdatedUUID())
                .stockId(e.getStockId())
                .stockSymbol(e.getStockSymbol())
                .stockExchange(e.getStockExchange())
                .stockTreadingStartTime(e.getStockTreadingStartTime())
                .stockTreadingEndTime(e.getStockTreadingEndTime())
                .fsmStocksBucket(e.getFsmStocksBucket() != null ? fsmStocksBucketMapping(e.getFsmStocksBucket()) : null)
                .build();
    }

    public static DailyStockTriggering dailyStockTriggeringMapping(DailyStockTriggeringDTO d) {
        if (d == null) return null;
        DailyStockTriggering e = new DailyStockTriggering();
        e.setStockPriceUpdatedUUID(d.getStockPriceUpdatedUUID());
        e.setStockId(d.getStockId());
        e.setStockSymbol(d.getStockSymbol());
        e.setStockExchange(d.getStockExchange());
        e.setStockTreadingStartTime(d.getStockTreadingStartTime());
        e.setStockTreadingEndTime(d.getStockTreadingEndTime());
        return e;
    }

    public static TrackStockPriceActionDTO trackStockPriceActionMapping(TrackStockPriceAction e) {
        if (e == null) return null;
        return TrackStockPriceActionDTO.builder()
                .stockPriceActionUUID(e.getStockPriceActionUUID())
                .priceActionID(e.getPriceActionID())
                .stockId(e.getStockId())
                .stockName(e.getStockName())
                .currentPrice(e.getCurrentPrice())
                .openingPrice(e.getOpeningPrice())
                .closingPrice(e.getClosingPrice())
                .tradingDate(e.getTradingDate())
                .tradingDay(e.getTradingDay())
                .updatedDateTime(e.getUpdatedDateTime())
                .updateDay(e.getUpdateDay())
                .fsmStocksBucket(e.getFsmStocksBucket() != null ? fsmStocksBucketMapping(e.getFsmStocksBucket()) : null)
                .build();
    }

    public static TrackStockPriceAction trackStockPriceActionMapping(TrackStockPriceActionDTO d) {
        if (d == null) return null;
        TrackStockPriceAction e = new TrackStockPriceAction();
        e.setStockPriceActionUUID(d.getStockPriceActionUUID());
        e.setPriceActionID(d.getPriceActionID());
        e.setStockId(d.getStockId());
        e.setStockName(d.getStockName());
        e.setCurrentPrice(d.getCurrentPrice());
        e.setOpeningPrice(d.getOpeningPrice());
        e.setClosingPrice(d.getClosingPrice());
        e.setTradingDate(d.getTradingDate());
        e.setTradingDay(d.getTradingDay());
        e.setUpdatedDateTime(d.getUpdatedDateTime());
        e.setUpdateDay(d.getUpdateDay());
        return e;
    }
}
