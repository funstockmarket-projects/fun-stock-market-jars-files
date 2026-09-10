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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "TRADEORDERS", schema = "ADMIN")
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
    @Column(name = "EXCHANGENAME", nullable = false, length = 10)
    @OneToMany (fetch = FetchType.LAZY)
    private String exchangeName;
    @Column(name = "STOCKBUCKETIDINFSM")
    private Long stockBucketIdInFsm;
    @Column(name = "STOCKSYMBOL", nullable = false, length = 50)
    private String stockSymbol;
    @Column(name = "QUANTITY", nullable = false)
    private Long quantity = 0L;
    @Column(name = "TRADEAT", precision = 15, scale = 2, nullable = false)
    private BigDecimal tradeAt = BigDecimal.ZERO;
    @Column(name = "BROKERNAME", nullable = false, length = 255)
    private Long brokerName;
    @Enumerated(EnumType.STRING)
    @Column(name = "ORDERWINDOW", nullable = false, length = 10)
    private OrderWindow orderWindow;
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
    @Column(name = "TRADEORDERSTATUSINFSMACCOUNT", nullable = false, length = 20)
    private OrderStatus tradeOrderStatusInFsmAccount = OrderStatus.PENDING;
    @Enumerated(EnumType.STRING)
    @Column(name = "STOCKDETAILSAVALIBILITYINFSM", nullable = false, length = 20)
    private StockAvailability stockDetailsAvalibilityInFsm = StockAvailability.UNAVALIBLE;
    @Enumerated(EnumType.STRING)
    @Column(name = "STOCKAPPROVEDSTATUSINFSM", nullable = false, length = 20)
    private PerformanceStatus stockApprovedStatusInFsm;
    @Column(name = "FSMSTOCKDESCRIPTION", length = 255)
    private String fsmStockDescription;
    @Enumerated(EnumType.STRING)
    @Column(name = "RECORDSTATUS", length = 10)
    private RecordStatus recordStatus;
    @Column(name = "RECORDCREATEDORMODIFIEDDATETIME", insertable = false, updatable = false)
    private LocalDateTime recordCreatedOrModifiedDateTime;
    @MapsId
    @JsonIgnore
    @OneToMany (fetch = FetchType.LAZY)
    @JoinColumn(name = "EXCHANGENAME", referencedColumnName = "exchangeName", insertable = false, updatable = false)
    private StockExchange stockExchange;
    @OneToMany(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @JsonIgnore 
    private FSM_Users fsmUsers;
    @OneToMany (fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "BROKERNAME", referencedColumnName = "BROKERID", insertable = false, updatable = false)
    @JsonIgnore
    private Broker broker;
    @OneToOne(mappedBy = "tradeOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference 
    private ClearingTradeOrder clearingTradeOrder;
    @MapsId 
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCKBUCKETIDINFSM", referencedColumnName = "BUCKETID", insertable = false, updatable = false)
    private FSM_StockDetailsBucket stockDetailsBucket;

}
