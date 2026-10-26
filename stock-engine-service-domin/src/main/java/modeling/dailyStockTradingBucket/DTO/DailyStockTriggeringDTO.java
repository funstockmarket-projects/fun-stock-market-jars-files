package modeling.dailyStockTradingBucket.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyStockTriggeringDTO {
    private String stockPriceUpdatedUUID;
    private Long stockId;
    private String stockSymbol;
    private String stockExchange;
    private LocalDateTime stockTreadingStartTime;
    private LocalDateTime stockTreadingEndTime;
    private FsmStocksBucketDTO fsmStocksBucket;
}