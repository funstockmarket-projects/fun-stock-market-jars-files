package modeling.FsmStockExchange.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;

import java.time.LocalDateTime;

@Builder
@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
public class StockExchangeDTO {

    public Long id;
    public String exchangeUuid;
    public String exchangeName;
    public PerformanceStatus exchangeStatus;
    @Builder.Default
    public RecordStatus recordStatus = RecordStatus.ADDED;
    @Builder.Default
    public LocalDateTime recordCreatedOrModifiedDateTime = LocalDateTime.now();
}

