package modeling.FsmHoldings.DTO;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class InsertStockDTO {
    private int totalStockHoldings;
    private BigDecimal currentValue;
    private BigDecimal totalInvestment;
}
