package org.Analysis.priceModels;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDetails {
    private String stockName;
    private Map<String, Integer> stockQuantity;
    private BigDecimal totalInvestment;
    private Map<String, BigDecimal> monthlyPerformance;
    private Map<String, BigDecimal> monthlyProfitAndLoss;
    private Number averageMonthlyProfit;
    private Number averagePortfolioSize;
}
