package modeling.FsmHoldings.enitity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import modeling.FsmHoldings.constants.TypeOfHoldingsAccount;
import modeling.fsmUsers.userEntity.FSM_Users;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "holdings", schema = "ADMIN")
public class UserHoldings {

    @Id
    @Column(name = "holdings_userId", nullable = false, updatable = false, insertable = false)
    private long id;
    @Column(name = "holdings_UUID", nullable = false, updatable = false, insertable = false)
    private String holdingUUID;
    @Column(name= "userName",nullable = false, updatable = false, insertable = false)
    private String userName;
    @Column(name = "typeOfHoldingsAccount", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeOfHoldingsAccount typeOfHoldingsAccount;
    @Column(name = "totalStockHoldings", nullable = false)
    private int totalStockHoldings;
    @Column(name = "currentValue", nullable = false)
    private BigDecimal currentValue;
    @Column(name = "totalInvestment", nullable = false)
    private BigDecimal totalInvestment;
    @Column(name = "holdingsOpeningDateAndTime", nullable = false)
    private LocalDateTime holdingsOpeningDateAndTime;
    @Column(name = "holdingsActiveStatusCode", nullable = false)
    @Enumerated(EnumType.STRING)
    private PerformanceStatus accountStatus;
    @Column(name = "recordCreatedOrModifiedDateTime",nullable = false)
    private LocalDateTime recordCreatedOrModifiedDateTime;
    @Column(name = "recordStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holdings_userId", referencedColumnName = "userId",  insertable = false, updatable = false)
    private FSM_Users users;
}
