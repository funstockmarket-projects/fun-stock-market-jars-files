package modeling.FsmBroker.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modeling.globalEnums.RecordStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
@Setter 
@Getter 
@Data 
public class BrokerageChargesDTO {

    private Long brokerId;
    private String uuid;
    private String brokerIdentifier;
    @Builder.Default
    private BigDecimal equityDeliveryMin = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal equityDeliveryMax = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal equityIntradayMin = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal equityIntradayMax = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal equityFuturesMin = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal equityFuturesMax = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal equityOptions = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal dp = BigDecimal.ZERO;
    @Builder.Default
    private RecordStatus recordStatus = RecordStatus.ADDED;
    @Builder.Default
    private LocalDateTime recordCreatedOrModifiedDateTime = LocalDateTime.now();
}

