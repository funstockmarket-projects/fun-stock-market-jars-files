package modeling.trade.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modeling.globalEnums.ProcessingStatus;
import modeling.globalEnums.RecordStatus;
import modeling.globalEnums.YesOrNoStatusFlag;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder
public class ClearingDTO {
    private Long clearingId;
    private String clearingUuid;
    private Long accountId;
    private ProcessingStatus clearingStatusCode;
    private String clearingMessage;
    private YesOrNoStatusFlag isRejectedTrade;
    private RecordStatus recordStatus;
    private LocalDateTime recordCreatedOrModifiedDateTime;
}
