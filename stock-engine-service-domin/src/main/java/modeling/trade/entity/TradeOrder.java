package modeling.trade.entity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import modeling.globalEnums.YesOrNoStatusFlag;
import modeling.FsmBroker.entity.Broker;
import modeling.FsmStockExchange.entity.StockExchange;
import modeling.fsmBuckets.entities.FSM_StockDetailsBucket;
import modeling.fsmUsers.userEntity.FSM_Users;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;
import modeling.trade.constants.OrderStatus;
import modeling.trade.constants.OrderWindow;
import modeling.trade.constants.StockAvailability;
import modeling.trade.constants.TradeOrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "TRADE_ORDERS", schema = "ADMIN")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "clearingTradeOrder")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TradeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERID", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long orderId;
    @Column(name = "ORDERUUID", nullable = false, unique = true, length = 255, updatable = false)
    @EqualsAndHashCode.Include
    private String orderUuid;
    @Column(name = "USERID", nullable = false)
    private Long userId;
    @Column(name = "EXCHANGEID", nullable = false, length = 10)
    private Long exchangeId;
    @Column(name = "STOCKBUCKETIDINFSM")
    private Long stockBucketIdInFsm;
    @Column(name = "STOCKSYMBOL", nullable = false, length = 50)
    private String stockSymbol;
    @Column(name = "QUANTITY", nullable = false)
    private Long quantity = 0L;
    @Column(name = "TRADEAT", precision = 15, scale = 2, nullable = false)
    private BigDecimal tradeAt = BigDecimal.ZERO;
    @Column(name = "BROKERID", nullable = false, length = 255)
    private Long brokerId;
    @Enumerated(EnumType.STRING)
    @Column(name = "ORDERWINDOW", nullable = false, length = 10)
    private OrderWindow orderWindow;
    @Column(name = "ORGINALTRADEORDERUUID")
    private String orginalTradeOrderUUID;
    @Enumerated(EnumType.STRING)
    @Column(name = "TRADEORDERTYPE", nullable = false, length = 10)
    private TradeOrderType tradeOrderType;
    @Enumerated(EnumType.STRING)
    @Column(name = "TRADECLEARINGPROCESSDONE", length = 1)
    private YesOrNoStatusFlag tradeClearingProcessDone;
    @Column(name = "TRADEORDERBUSINESSDATETIME")
    private LocalDateTime tradeOrderBusinessDateTime;
    @Column(name = "TRADEORDERDATEINBROKERACCOUNT")
    private LocalDateTime tradeOrderDateInBrokerAccount;
    @Column(name = "FSMTRADEORDERREGESTERDATETIME")
    private LocalDateTime fsmTradeOrderRegesterDateTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "TRADEORDERSTATUSINORIGINALBROKERACCOUNT", nullable = false, length = 20)
    private OrderStatus tradeOrderStatusInOriginalBrokerAccount;
    @Enumerated(EnumType.STRING)
    @Column(name = "TRADEORDERSTATUSINFSMACCOUNT", length = 20)
    private OrderStatus tradeOrderStatusInFsmAccount = OrderStatus.PENDING;
    @Enumerated(EnumType.STRING)
    @Column(name = "STOCKDETAILSAVALIBILITYINFSM", length = 20)
    private StockAvailability stockDetailsAvalibilityInFsm = StockAvailability.UNAVALIBLE;
    @Enumerated(EnumType.STRING)
    @Column(name = "STOCKAPPROVEDSTATUSINFSM", length = 20)
    private PerformanceStatus stockApprovedStatusInFsm;
    @Column(name = "FSMSTOCKDESCRIPTION", length = 255)
    private String fsmStockDescription;
    @Enumerated(EnumType.STRING)
    @Column(name = "RECORDSTATUS", length = 10, insertable = false, updatable = false)
    private RecordStatus recordStatus;
    @Column(name = "RECORDCREATEDORMODIFIEDDATETIME", insertable = false, updatable = false)
    private LocalDateTime recordCreatedOrModifiedDateTime;
    @Column(name="CLEARINGBUSINESSDATE")
    private LocalDateTime clearingBusinessDate;
    @JsonManagedReference 
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "EXCHANGEID", referencedColumnName = "EXCHANGEID", insertable = false, updatable = false)
    private StockExchange stockExchange;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @JsonManagedReference  
    private FSM_Users fsmUsers;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BROKERID", referencedColumnName = "BROKERID", insertable = false, updatable = false)
    @JsonManagedReference 
    private Broker broker;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "TRADEORDERID")  
    @JsonManagedReference 
    private List<ClearingTradeOrder> clearingTradeOrder;
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCKBUCKETIDINFSM", referencedColumnName = "BUCKETID", insertable = false, updatable = false)
    @JsonManagedReference
    private FSM_StockDetailsBucket stockDetailsBucket;

}
