package modeling.dailyStockTradingBucket.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackStockPriceActionDTO {
    private String stockPriceActionUUID;
    private Long priceActionID;
    private Long stockId;
    private String stockName;
    private BigDecimal currentPrice;
    private BigDecimal openingPrice;
    private BigDecimal closingPrice;
    private LocalDateTime tradingDate;
    private String tradingDay;
    private LocalDateTime updatedDateTime;
    private String updateDay;
    private FsmStocksBucketDTO fsmStocksBucket;
}
