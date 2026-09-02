package modeling.fsmBuckets.entities;

import jakarta.persistence.*;
import lombok.Data;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;

import java.time.LocalDateTime;

@Entity
@Table(name="stockExchange")
@Data
public class StockExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="exchangeId")
    private long id;
    @Column(name="exchangeUUID",unique= true)
    private String exchangeUuid;
    @Column(name="exchangeName",unique = true,nullable = false,length=10)
    private String exchangeName;
    @Column(name="exchangeStatus",nullable = false,length = 20)
    @Enumerated(EnumType.STRING)
    private PerformanceStatus exchangeStatus= PerformanceStatus.ACTIVE;
    @Column(name="recordStatus",nullable = false,length = 10)
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus=RecordStatus.ADDED;
    @Column(name="recordCreatedOrModifiedDateTime")
    private LocalDateTime recordUpdatedTime;
}
