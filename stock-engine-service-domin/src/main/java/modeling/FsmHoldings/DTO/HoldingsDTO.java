package modeling.FsmHoldings.DTO;

import lombok.Builder;
import lombok.Data;
import modeling.FsmHoldings.constants.TypeOfHoldingsAccount;
import modeling.fsmUsers.DTO.FSM_UserDetailsDTO;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.RecordStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class HoldingsDTO {

    public UUID holdingUUID;
    public String userName;
    public TypeOfHoldingsAccount typeOfHoldingsAccount;
    public int totalStockHoldings;
    public BigDecimal currentValue;
    public BigDecimal totalInvestment;
    public LocalDateTime holdingsOpeningDateAndTime;
    public PerformanceStatus accountStatus;
    public LocalDateTime recordCreatedOrModifiedDateTime;
    public RecordStatus recordStatus;
    public FSM_UserDetailsDTO users;
}
