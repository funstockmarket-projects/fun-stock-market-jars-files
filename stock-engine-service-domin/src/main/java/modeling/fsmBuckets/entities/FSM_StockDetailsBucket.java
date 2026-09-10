package modeling.fsmBuckets.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modeling.FsmStockExchange.entity.StockExchange;
import modeling.fsmUsers.userEntity.FSM_Users;
import modeling.globalEnums.CapType;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.StockStatusInMarket;
import modeling.globalEnums.ProcessingStatus;
import modeling.globalEnums.RecordStatus;
import modeling.globalEnums.YesOrNoStatusFlag;
import modeling.trade.constants.StockAvailability;
import modeling.trade.entity.TradeOrder;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Setter 
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="FSM_STOCK_DETAILS_BUCKET", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"STOCKSYMBOL", "EXCHANGENAME"}, name = "uq_stock_exchange"),
}, schema = "ADMIN")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FSM_StockDetailsBucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUCKETID", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long bucketId;
    @Column(name = "BUCKETUUID", nullable = false, unique = true, length = 255)
    @EqualsAndHashCode.Include
    private String bucketUuid;
    @Column(name = "STOCKREGISTERWITHTRADORDERID", nullable = false, unique = true)
    private Long stockRegisterWithTradeOrderId;
    @Column(name = "STOCKADDEDTHROUGHUSERID", nullable = false, updatable = false)
    @OneToMany(fetch = FetchType.LAZY)
    private Long stockAddedThroughUserId;
    @Column(name = "EXCHANGENAME", nullable = false)
    @OneToMany (fetch = FetchType.LAZY)
    private Long exchangeName;
    @Column(name = "STOCKSYMBOL", nullable = false, length = 50)
    private String stockSymbol;
    @Column(name = "STOCKNAME", nullable = false, length = 60)
    private String stockName;
    @Column(name = "FSMSTOCKIDENTIFICATION", unique = true, insertable = false, updatable = false)
    private String fsmStockIdentification;
    @Column(name = "ISSTOCKFOUNDINGLOBALMARKET", nullable = false, length = 1)
    @Enumerated (EnumType.STRING)
    private YesOrNoStatusFlag isStockFoundInMarket;
    @Column(name = "LISTEDDATEINFSM")
    private LocalDateTime listedDateInFsm;
    @Column(name = "LISTEDDAYINFSM", length = 9)
    @Enumerated(EnumType.STRING)
    private DayOfWeek listedDayInFsm;
    @Column(name = "FSMSTOCKPROCESSEDDATETIME", insertable = false)
    private LocalDateTime fsmStockProcessedDateTime;
    @Column(name = "FSMSTOCKPROCESSEDDAY", length = 9, insertable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek fsmStockProcessedDay;
    @Column(name = "STOCKTRADINGSTARTTIME", nullable = false)
    private LocalDateTime stockTradingStartTime;
    @Column(name = "STOCKTRADINGENDTIME", nullable = false)
    private LocalDateTime stockTradingEndTime;
    @Column(name = "CAPTYPE", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private CapType capType ;
    @Column(name = "PROCESSINGSTATUS", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private ProcessingStatus processingStatus;
    @Column(name = "STOCKSTATUSINMARKET", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private StockStatusInMarket stockStatusInMarket ;
    @Column(name = "STOCKSTATUSINFSM", nullable = false, length = 9)
    @Enumerated(EnumType.STRING)
    private PerformanceStatus stockStatusInFsm;
    @Column(name = "TRADINGSTATUS", nullable = false, length = 9)
    @Enumerated(EnumType.STRING)
    private PerformanceStatus tradingStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "STOCKDETAILSAVALIBLEFROMBROKER", nullable = false, length = 20)
    private StockAvailability stockDetailsAvalibilityFromBroker = StockAvailability.UNAVALIBLE;
    @OneToMany(fetch = FetchType.LAZY)
    @MapsId 
    @JoinColumn (name = "STOCKADDEDTHROUGHUSERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @JsonIgnore 
    private FSM_Users fsm_Users;
    @OneToOne (fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "STOCKREGISTERWITHTRADORDERID", referencedColumnName = "ORDERID", insertable = false, updatable = false)
    @JsonIgnore
    private TradeOrder tradeOrder;
    @MapsId 
    @JsonIgnore 
    @OneToMany (fetch = FetchType.LAZY)
    @JoinColumn(name = "EXCHANGENAME", referencedColumnName = "exchangeId", insertable = false, updatable = false)
    private StockExchange stockExchange;
    @Column(name = "RECORDCREATIONORMODIFIEDDATETIME", insertable = false, updatable = false)
    private LocalDateTime recordCreationOrModifiedDateTime;
    @Column(name = "RECORDSTATUS", length = 10, insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus;
}