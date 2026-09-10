package modeling.trade.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import modeling.globalEnums.ProcessingStatus;
import modeling.globalEnums.RecordStatus;
import modeling.globalEnums.YesOrNoStatusFlag;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CLEARINGTRADEORDER")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "tradeOrder")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClearingTradeOrder {

    @Id
    @Column(name = "CLEARINGID", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long clearingId;
    @Column(name = "CLEARINGUUID", nullable = false, unique = true, length = 32, updatable = false)
    @EqualsAndHashCode.Include
    private String clearingUuid;
    @Column(name = "ACCOUNTID", nullable = false)
    private Long accountId;
    @Column(name = "CLEARINGSTATUSCODE", nullable = false, length = 20)
    private ProcessingStatus clearingStatusCode;
    @Column(name = "CLEARINGMESSAGE", nullable = false, length = 255)
    private String clearingMessage = " ";
    @Enumerated(EnumType.STRING)
    @Column(name = "ISREJECTEDTRADE", nullable = false, length = 1)
    private YesOrNoStatusFlag isRejectedTrade;
    @Enumerated(EnumType.STRING)
    @Column(name = "RECORDSTATUS", nullable = false, length = 10)
    private RecordStatus recordStatus;
    @Column(name = "RECORDCREATEDORMODIFIEDDATETIME", insertable = false, updatable = false)
    private LocalDateTime recordCreatedOrModifiedDateTime;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "CLEARINGID")
    @JsonIgnore 
    private TradeOrder tradeOrder;
}
