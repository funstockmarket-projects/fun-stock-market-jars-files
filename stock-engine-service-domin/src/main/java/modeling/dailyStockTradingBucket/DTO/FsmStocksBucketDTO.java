package modeling.dailyStockTradingBucket.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modeling.FsmStockExchange.DTO.StockExchangeDTO;
import modeling.dailyStockTradingBucket.enums.PriceStatus;
import modeling.fsmBuckets.DTO.FSM_StockDetailsBucketDTO;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FsmStocksBucketDTO {
    private String stockUuid;
    private Long stockId;
    private String stockSymbol;
    private String exchangeName;
    private PerformanceStatus stockStatus;
    private LocalDateTime stockTreadingStartTime;
    private LocalDateTime stockTreadingEndTime;
    private LocalDateTime currentTradedDate;
    private String currentTradedDay;
    private LocalDateTime lastTradedDate;
    private String lastTradedDay;
    private PriceStatus lastTreadingDayPriceStatus;
    private PriceStatus currentTreadinfDayPriceStatus;
    private RecordStatus recordStatus;
    private LocalDateTime recordCreatedOrModifiedDateTime;

    private FSM_StockDetailsBucketDTO stockDetailsBucket;
    private StockExchangeDTO stockExchange;
}