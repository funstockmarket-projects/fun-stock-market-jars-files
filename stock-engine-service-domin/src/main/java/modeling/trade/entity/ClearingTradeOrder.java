package modeling.trade.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modeling.fsmUsers.userEntity.FSM_Users;
import modeling.globalEnums.ProcessingStatus;
import modeling.globalEnums.RecordStatus;
import modeling.globalEnums.YesOrNoStatusFlag;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CLEARING_TRADE_ORDER", schema = "ADMIN")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClearingTradeOrder {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLEARINGID", nullable = false, updatable = false)
    private Long clearingId;
    @Column(name = "CLEARINGUUID", nullable = false, unique = true, length = 36, updatable = false)
    @EqualsAndHashCode.Include
    private String clearingUuid;
    @Column(name = "ACCOUNTID", nullable = false)
    private Long accountId;
    @Column(name = "CLEARINGSTATUSCODE", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ProcessingStatus clearingStatusCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRADEORDERID", nullable = false)
    private TradeOrder tradeOrder;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNTID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @JsonIgnore 
    private FSM_Users fsmUsers;
}
