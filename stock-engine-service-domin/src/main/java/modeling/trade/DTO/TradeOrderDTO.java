package modeling.trade.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modeling.FsmBroker.DTO.BrokerDTO;
import modeling.FsmStockExchange.DTO.StockExchangeDTO;
import modeling.fsmUsers.DTO.FSM_UserDetailsDTO;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;
import modeling.globalEnums.YesOrNoStatusFlag;
import modeling.trade.constants.OrderStatus;
import modeling.trade.constants.OrderWindow;
import modeling.trade.constants.StockAvailability;
import modeling.trade.constants.TradeOrderType;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
public class TradeOrderDTO {

    private Long orderId;
    private String orderUuid;
    private Long userId;
    private Long stockBucketIdInFsm;
    private String stockSymbol;
    private Long quantity;
    private BigDecimal tradeAt;
    private Long brokerId;
    private OrderWindow orderWindow;
    private String orginalTradeOrderUUID;
    private TradeOrderType tradeOrderType;
    private YesOrNoStatusFlag tradeClearingProcessDone;
    private LocalDateTime tradeOrderBusinessDateTime;
    private LocalDateTime tradeOrderDateInBrokerAccount;
    private LocalDateTime fsmTradeOrderRegesterDateTime;
    private OrderStatus tradeOrderStatusInOriginalBrokerAccount;
    private OrderStatus tradeOrderStatusInFsmAccount;
    private StockAvailability stockDetailsAvalibilityInFsm;
    private PerformanceStatus stockApprovedStatusInFsm;
    private String fsmStockDescription;
    private RecordStatus recordStatus;
    private LocalDateTime recordCreatedOrModifiedDateTime;
    private Long exchangeId;
    private StockExchangeDTO stockExchangeDTO;
    private LocalDateTime clearingBusinessDate;
    private FSM_UserDetailsDTO fsmUsersDTO;
    private BrokerDTO stockBrokerDTO;
    private List<ClearingDTO> clearingTradeOrderDTO;
}
