package modeling.dailyStockTradingBucket.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "daily_stock_triggering")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyStockTriggering {

    @Column(name = "stockPriceUpdatedUUID", unique = true, nullable = false)
    private String stockPriceUpdatedUUID;

    @Id
    @Column(name = "stockId", nullable = false)
    private Long stockId;

    @Column(name = "stockSymbol", nullable = false)
    private String stockSymbol;

    @Column(name = "stockExchange", length = 10, nullable = false)
    private String stockExchange;

    @Column(name = "stockTreadingStartTime", nullable = false)
    private LocalDateTime stockTreadingStartTime;

    @Column(name = "stockTreadingEndTime", nullable = false)
    private LocalDateTime stockTreadingEndTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockId", referencedColumnName = "stockId", insertable = false, updatable = false)
    private FsmStocksBucket fsmStocksBucket;
}
