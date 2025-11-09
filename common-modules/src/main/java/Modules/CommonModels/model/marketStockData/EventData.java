package Modules.CommonModels.model.marketStockData;

import Modules.CommonModels.enums.MarketEvents;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventData {
    private String eventUUID;
    private MarketEvents eventName;
    private LocalDateTime EventCreationData;
    private LocalDateTime ModifiedDate = LocalDateTime.now();
    private List<StockFileDetails> stockFileDetails;

    public void isValid(){
        this.ModifiedDate = LocalDateTime.now();
    }
}
