package modeling.FsmBroker.entity;

import jakarta.persistence.*;
import lombok.*;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "broker", schema = "ADMIN")
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brokerId", nullable = false)
    private Long id;
    @Column(name = "brokerUuid", unique = true, length = 255)
    private String brokerUuid;
    @Column(name = "brokerIdentifier", unique = true, length = 30)
    private String brokerIdentifier;
    @Column(name = "brokerName", length = 50)
    private String brokerName;
    @Column(name = "nseCode", length = 10)
    private String nseCode;
    @Column(name = "bseCode", length = 10)
    private String bseCode;
    @Column(name = "sebiRegNo", length = 12)
    private String sebiRegNo;
    @Column(name = "depository", length = 5)
    private String depository;
    @Column(name = "brokerType", length = 17)
    private String brokerType;
    @Column(name = "sector", length = 7)
    private String sector;
    @Column(name = "brokerStatus", length = 9)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PerformanceStatus brokerStatus = PerformanceStatus.ACTIVE;
    @Column(name = "recordStatus", length = 8)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RecordStatus recordStatus = RecordStatus.ADDED;
    @Column(name = "recordCreatedOrModifiedDateTime")
    @Builder.Default
    private LocalDateTime recordCreatedOrModifiedDateTime = LocalDateTime.now();
    @OneToOne(mappedBy = "broker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference 
    private BrokerageCharges brokerageCharges;
}
