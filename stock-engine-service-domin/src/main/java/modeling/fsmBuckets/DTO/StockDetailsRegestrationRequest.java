package modeling.fsmBuckets.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDetailsRegestrationRequest {

    
    @NotBlank(message = "Stock symbol cannot be blank")
    @Size(min = 3, max = 10, message = "Stock symbol must be between 3 and 10 characters")
    private String stockSymbol;

    @NotBlank(message = "Exchange name cannot be blank")
    @Size(max = 50, message = "Exchange name must not exceed 50 characters")
    private String exchangeName;

    @NotNull(message = "User identity cannot be null")
    @Positive(message = "User identity must be positive")
    private Long stockAddedThroughUserId;

    @NotNull(message = "Trade identity cannot be null")
    @Positive(message = "Trade identity must be positive")
    private Long stockRegisterWithTradeOrderId;
}
