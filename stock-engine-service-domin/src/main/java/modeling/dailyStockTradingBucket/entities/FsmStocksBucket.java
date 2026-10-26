package modeling.dailyStockTradingBucket.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modeling.FsmStockExchange.entity.StockExchange;
import modeling.dailyStockTradingBucket.enums.PriceStatus;
import modeling.fsmBuckets.entities.FSM_StockDetailsBucket;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "fsm_stocks_bucket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FsmStocksBucket {

    @Column(name = "stockUuid", unique = true, updatable = false)
    private String stockUuid;

    @Id
    @Column(name = "stockId")
    private Long stockId;

    @Column(name = "stockSymbol", nullable = false)
    private String stockSymbol;

    @Column(name = "exchangeName", length = 10, nullable = false)
    private String exchangeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "stockStatus", nullable = false)
    private PerformanceStatus stockStatus;

    @Column(name = "stockTreadingStartTime", nullable = false)
    private LocalDateTime stockTreadingStartTime;

    @Column(name = "stockTreadingEndTime", nullable = false)
    private LocalDateTime stockTreadingEndTime;

    @Column(name = "currentTradedDate")
    private LocalDateTime currentTradedDate;

    @Column(name = "currentTradedDay")
    private String currentTradedDay;

    @Column(name = "lastTradedDate")
    private LocalDateTime lastTradedDate;

    @Column(name = "lastTradedDay")
    private String lastTradedDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "lastTreadingDayPriceStatus")
    private PriceStatus lastTreadingDayPriceStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "currenttreadinfDayPriceStatus")
    private PriceStatus currentTreadinfDayPriceStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "recordStatus", nullable = false)
    private RecordStatus recordStatus;

    @Column(name = "recordCreatedOrModifiedDateTime")
    private LocalDateTime recordCreatedOrModifiedDateTime;

    // Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockId", referencedColumnName = "bucketID", insertable = false, updatable = false)
    private FSM_StockDetailsBucket stockDetailsBucket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchangeName", referencedColumnName = "exchangeName", insertable = false, updatable = false)
    private StockExchange stockExchange;
}
