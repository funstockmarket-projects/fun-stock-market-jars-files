package modeling.dailyStockTradingBucket.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "track_stock_price_action")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackStockPriceAction {

    @Column(name = "stockPriceActionUUID", unique = true)
    private String stockPriceActionUUID;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priceActionID")
    private Long priceActionID;

    @Column(name = "stockId", nullable = false)
    private Long stockId;

    @Column(name = "stockName")
    private String stockName;

    @Column(name = "currentPrice", precision = 15, scale = 2)
    private BigDecimal currentPrice;

    @Column(name = "openingPrice", precision = 15, scale = 4)
    private BigDecimal openingPrice;

    @Column(name = "closingPrice", precision = 15, scale = 4)
    private BigDecimal closingPrice;

    @Column(name = "tradingDate")
    private LocalDateTime tradingDate;

    @Column(name = "tradingDay")
    private String tradingDay;

    @Column(name = "updatedDateTime")
    private LocalDateTime updatedDateTime;

    @Column(name = "updateDay")
    private String updateDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockId", referencedColumnName = "stockId", insertable = false, updatable = false)
    private FsmStocksBucket fsmStocksBucket;
}