package modeling.FsmBroker.entity;


import jakarta.persistence.*;
import lombok.*;
import modeling.globalEnums.RecordStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "brokerageCharges", schema = "ADMIN")
public class BrokerageCharges {

    @Id
    @Column(name = "brokerId", nullable = false)
    private Long brokerId;
    @Column(name = "uuid", unique = true, length = 255)
    private String uuid;
    @Column(name = "brokerIdentifier", nullable = false, unique = true, length = 40)
    private String brokerIdentifier;
    @Column(name = "equityDeliveryMin", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal equityDeliveryMin = BigDecimal.ZERO;
    @Column(name = "equityDeliveryMax", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal equityDeliveryMax = BigDecimal.ZERO;
    @Column(name = "equityIntradayMin", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal equityIntradayMin = BigDecimal.ZERO;
    @Column(name = "equityIntradayMax", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal equityIntradayMax = BigDecimal.ZERO;
    @Column(name = "equityFuturesMin", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal equityFuturesMin = BigDecimal.ZERO;
    @Column(name = "equityFuturesMax", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal equityFuturesMax = BigDecimal.ZERO;
    @Column(name = "equityOptions", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal equityOptions = BigDecimal.ZERO;
    @Column(name = "dp", precision = 15, scale = 4)
    @Builder.Default
    private BigDecimal dp = BigDecimal.ZERO;
    @Column(name = "recordStatus", length = 10)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RecordStatus recordStatus = RecordStatus.ADDED;
    @Column(name = "recordCreatedOrModifiedDateTime")
    @Builder.Default
    private LocalDateTime recordCreatedOrModifiedDateTime = LocalDateTime.now();
    @OneToOne
    @MapsId
    @JsonIgnore   
    @JoinColumn(name = "brokerId", insertable = false, updatable = false)
    private Broker broker;
}

