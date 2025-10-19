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
    private List<StockFileDetails> stockFileDetails;
    private LocalDateTime EventCreationData;
    private LocalDateTime ModifiedDate = LocalDateTime.now();


    public boolean isValid(){
        return false;
    }
}
