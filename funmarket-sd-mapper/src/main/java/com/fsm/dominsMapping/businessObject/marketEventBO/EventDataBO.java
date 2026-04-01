package com.fsm.dominsMapping.businessObject.marketEventBO;


import com.fsm.dominsMapping.businessObject.stockDetailsBO.StockFileDetailsBO;
import com.fsm.dominsMapping.constantsBO.MarketEventsBO;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDataBO {

    private String eventUUID;

    private MarketEventsBO eventNameBO;
    private LocalDateTime EventCreationData;
    private LocalDateTime ModifiedDate = LocalDateTime.now();
    private List<StockFileDetailsBO> stockFileDetailsBO;
    public void isValid(){
        this.ModifiedDate = LocalDateTime.now();
    }
}
