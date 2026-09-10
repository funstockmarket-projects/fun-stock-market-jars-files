package modeling.FsmStockExchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;

import java.time.LocalDateTime;

@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
@Entity
@Table(name = "STOCKEXCHANGE", schema = "ADMIN")
public class StockExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchangeId", nullable = false)
    private Long id;

    @Column(name = "exchangeUUID", unique = true, length = 36)
    private String exchangeUuid;

    @Column(name = "exchangeName", unique = true, nullable = false, length = 10)
    private String exchangeName;

    @Column(name = "exchangeStatus", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PerformanceStatus exchangeStatus = PerformanceStatus.ACTIVE;

    @Column(name = "recordStatus", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RecordStatus recordStatus = RecordStatus.ADDED;

    @Column(name = "recordCreatedOrModifiedDateTime")
    @Builder.Default
    private LocalDateTime recordCreatedOrModifiedDateTime = LocalDateTime.now();
}

