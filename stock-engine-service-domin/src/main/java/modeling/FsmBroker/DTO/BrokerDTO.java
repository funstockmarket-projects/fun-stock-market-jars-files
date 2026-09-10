package modeling.FsmBroker.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;

import java.time.LocalDateTime;

@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
@Setter 
@Getter 
@Data 
public class BrokerDTO {

    private Long id;
    private String brokerUuid;
    private String brokerIdentifier;
    private String brokerName;
    private String nseCode;
    private String bseCode;
    private String sebiRegNo;
    private String depository;
    private String brokerType;
    private String sector;
    @Builder .Default
    private PerformanceStatus brokerStatus = PerformanceStatus.ACTIVE;
    @Builder.Default
    private RecordStatus recordStatus= RecordStatus.ADDED;
    @Builder.Default
    private LocalDateTime recordCreatedOrModifiedDateTime = LocalDateTime.now();
    private BrokerageChargesDTO brokerageCharges;
}

