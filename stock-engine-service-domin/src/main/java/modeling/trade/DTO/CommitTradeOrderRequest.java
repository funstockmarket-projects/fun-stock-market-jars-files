package modeling.trade.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modeling.trade.constants.OrderWindow;
import modeling.trade.constants.TradeOrderType;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
public class CommitTradeOrderRequest {

    private Long userId;
    private String username;
    @NotBlank(message = "Stock symbol cannot be blank")
    private String stockSymbol;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Long quantity;
    @NotNull(message = "Trading price is required")
    @Min(value = 1, message = "Trading price must be at least 1")
    private BigDecimal tradeAt;
    private String brokerName;
    @NotBlank (message = "Order window cannot be blank, please provide either 'BUY' or 'SELL'")
    private OrderWindow orderWindow;
    @Builder.Default
    private TradeOrderType tradeOrderType = TradeOrderType.OBSERVABLE;
    @Builder.Default
    private String exchangeName = "NSE";

}
