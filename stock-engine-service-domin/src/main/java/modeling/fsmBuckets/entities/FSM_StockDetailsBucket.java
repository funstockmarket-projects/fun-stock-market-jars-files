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
@Table(name = "FSM_STOCK_BUCKET", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"STOCKSYMBOL", "EXCHANGENAME"}, name = "uq_stock_exchange")
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

    @Column(name = "STOCKSYMBOL", nullable = false, length = 50)
    private String stockSymbol;

    @Column(name = "STOCKNAME", length=50)
    private String stockName;

    // Fixed: Database schema defines exchangeName as VARCHAR2(10), not Long
    @Column(name = "EXCHANGENAME", nullable = false, length = 10)
    private String exchangeName;

    @Column(name = "FSMSTOCKIDENTIFICATION", length = 87, unique = true, insertable = false, updatable = false)
    private String fsmStockIdentification;

    // Writable scalar foreign key IDs (Direct input supported)
    @Column(name = "STOCKADDEDTHROUGHUSERID", nullable = false)
    private Long stockAddedThroughUserId;

    @Column(name = "STOCKREGISTERWITHTRADEORDERID", nullable = false, unique = true)
    private Long stockRegisterWithTradeOrderId;

    @Column(name = "ISSTOCKFOUNDINGLOBALMARKET", length = 1)
    @Enumerated(EnumType.STRING)
    private YesOrNoStatusFlag isStockFoundInGlobalMarket;

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
    private CapType capType;

    @Column(name = "PROCESSINGSTATUS", length = 10)
    @Enumerated(EnumType.STRING)
    private ProcessingStatus processingStatus;

    @Column(name = "ISSTOCKSTATUSINMARKET", length = 9)
    @Enumerated(EnumType.STRING)
    private StockStatusInMarket isStockStatusInMarket;

    @Column(name = "STOCKSTATUSINFSM", length = 9)
    @Enumerated(EnumType.STRING)
    private PerformanceStatus stockStatusInFsm;

    @Column(name = "ISSTOCKDELISTEDMARKET", length = 9)
    @Enumerated(EnumType.STRING)
    private StockStatusInMarket isStockDelistedMarket;

    @Column(name = "TRADINGSTATUS", length = 9)
    @Enumerated(EnumType.STRING)
    private PerformanceStatus tradingStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "STOCKDETAILSAVALIBILITYFROMBROKER", length = 20)
    private StockAvailability stockDetailsAvalibilityFromBroker = StockAvailability.UNAVALIBLE;

    @Column(name = "STOCKAPPROVALSTATUS", length = 10)
    @Enumerated(EnumType.STRING)
    private ProcessingStatus stockApprovalStatus;

    @Column(name = "STOCKAPPROVEDBYADMINID")
    private Long stockApprovedByAdminId;

    @Column(name = "STOCKAPPROVEDDATETIME")
    private LocalDateTime stockApprovedDateTime;

    @Column(name = "STOCKREJECTEDBYADMINID")
    private Long stockRejectedByAdminId;

    @Column(name = "STOCKREJECTEDDATETIME")
    private LocalDateTime stockRejectedDateTime;

    @Column(name = "STOCKREJECTIONREASON", length = 255)
    private String stockRejectionReason;

    @Column(name = "RECORDCREATIONORMODIFIEDDATETIME", insertable = false, updatable = false)
    private LocalDateTime recordCreationOrModifiedDateTime;

    @Column(name = "RECORDSTATUS", length = 10, insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus;

    // --- Read-Only Relationships (Avoids write conflicts while allowing object navigation) ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCKADDEDTHROUGHUSERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @JsonIgnore 
    private FSM_Users fsmUsers;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCKREGISTERWITHTRADEORDERID", referencedColumnName = "ORDERID", insertable = false, updatable = false)
    @JsonIgnore
    private TradeOrder tradeOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXCHANGENAME", referencedColumnName = "EXCHANGENAME", insertable = false, updatable = false)
    @JsonIgnore 
    private StockExchange stockExchange;
}