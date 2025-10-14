package Modules.CommonModels.model;

import java.math.BigDecimal;
import java.util.Map;

public record StockPerformance(String StockName,
                               Map<String, String> stockQuantity,
                               BigDecimal totalInvestment,
                               Map<String, BigDecimal> monthlyPerformance
) {
    public boolean validate() {
        return StockName != null && !StockName.isEmpty() &&
               totalInvestment != null && totalInvestment.compareTo(BigDecimal.ZERO) >= 0 &&
               monthlyPerformance != null && !monthlyPerformance.isEmpty();
    }
}
