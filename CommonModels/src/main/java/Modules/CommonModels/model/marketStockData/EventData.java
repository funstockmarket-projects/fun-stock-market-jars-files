package Modules.CommonModels.model.marketStockData;

import Modules.CommonModels.enums.MarketEvents;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
