package modeling.fsmBuckets.DTO;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modeling.FsmStockExchange.DTO.StockExchangeDTO;
import modeling.fsmUsers.DTO.FSM_UserDetailsDTO;
import modeling.globalEnums.CapType;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.ProcessingStatus;
import modeling.globalEnums.RecordStatus;
import modeling.globalEnums.StockStatusInMarket;
import modeling.globalEnums.YesOrNoStatusFlag;
import modeling.trade.DTO.TradeOrderDTO;
import modeling.trade.constants.StockAvailability;

@Data
@Builder 
@AllArgsConstructor 
@NoArgsConstructor 
public class FSM_StockDetailsBucketDTO {

    private Long bucketId;
    private String bucketUuid;
    private String stockSymbol;
    private String stockName;
    private String exchangeName;
    private String fsmStockIdentification;
    private String instrument_key;


    // Raw foreign key IDs for direct insert/update without heavy entity fetching
    private Long stockAddedThroughUserId;
    private Long stockRegisterWithTradeOrderId;

    private YesOrNoStatusFlag isStockFoundInGlobalMarket;
    private LocalDateTime listedDateInFsm;
    private DayOfWeek listedDayInFsm;
    private LocalDateTime fsmStockProcessedDateTime;
    private DayOfWeek fsmStockProcessedDay;
    private LocalDateTime stockTradingStartTime;
    private LocalDateTime stockTradingEndTime;
    private CapType capType;
    private ProcessingStatus processingStatus;
    private StockStatusInMarket isStockStatusInMarket;
    private PerformanceStatus stockStatusInFsm;
    private StockStatusInMarket isStockDelistedMarket;
    private PerformanceStatus tradingStatus;
    private StockAvailability stockDetailsAvalibilityFromBroker;
    private ProcessingStatus stockApprovalStatus;
    private Long stockApprovedByAdminId;
    private LocalDateTime stockApprovedDateTime;
    private Long stockRejectedByAdminId;
    private LocalDateTime stockRejectedDateTime;
    private String stockRejectionReason;
    private LocalDateTime recordCreationOrModifiedDateTime;
    private RecordStatus recordStatus;

    // Optional nested objects populated during retrieval (DTO mapping)
    private FSM_UserDetailsDTO fsmUsers;
    private TradeOrderDTO tradeOrder;
    private StockExchangeDTO stockExchange;   
    private FSM_UserDetailsDTO stockRejectionAdminDetails;
    private FSM_UserDetailsDTO stockApprovalAdminDetails;



}
