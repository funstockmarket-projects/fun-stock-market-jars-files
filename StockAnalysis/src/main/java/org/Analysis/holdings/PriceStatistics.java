/*
Descriptive Statistics – Understanding Stock Data
------------------------------------------------------
Mean (Average Price) → Sum of all prices ÷ Number of days
Median → Middle price when sorted (less affected by outliers)
Variance → How much prices deviate from the average (spread)
Standard Deviation (SD) → Square root of variance; a measure of volatility
 */
package org.Analysis.holdings;

//import lombok.Getter;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.Analysis.holdings.Constants.*;
//
//@Getter
public class PriceStatistics {

//    private static final PortfolioRetrievals PORTFOLIO_RETRIEVALS;
//
//    static {
//        PORTFOLIO_RETRIEVALS = new PortfolioRetrievals();
//    }
//
//    private Map<String, BigDecimal> getPortfolioProfitAndLoss() {
//
//        Map<String, BigDecimal> totalInvestmentOfEveryMonthMap = PortfolioRetrievals.getInvestmentOfEveryMonth();
//        Map<String, BigDecimal> portfolioMonthlyProfitAndLossMap = PORTFOLIO_RETRIEVALS.getPortfolioOfEveryMonthProfitAndLoss();
//
//        Map<String, BigDecimal> profitAndLossMap = new HashMap<>();
//        if (totalInvestmentOfEveryMonthMap != null && portfolioMonthlyProfitAndLossMap != null) {
//            for (String month : totalInvestmentOfEveryMonthMap.keySet()) {
//                BigDecimal totalInvestment = totalInvestmentOfEveryMonthMap.getOrDefault(month, BigDecimal.ZERO);
//                BigDecimal portfolioValue = portfolioMonthlyProfitAndLossMap.getOrDefault(month, BigDecimal.ZERO);
//                if (totalInvestment != null && portfolioValue != null) {
//                    profitAndLossMap.put(month, portfolioValue.subtract(totalInvestment));
//                }
//            }
//        }
//        return PortfolioRetrievals.getSortedListByMonthName(profitAndLossMap);
//    }
//
//    private Map<String, BigDecimal> monthlyProfitAndLossDifference() {
//        Map<String, BigDecimal> monthlyProfitAndLossDifferenceMap = getPortfolioProfitAndLoss();
//        List<String> months = new ArrayList<>(monthlyProfitAndLossDifferenceMap.keySet());
//        Map<String, BigDecimal> monthlyProfitAndLossDifference = new HashMap<>();
//
//        for (int i = 1; i < months.size(); i++) {
//            String currentMonth = months.get(i);
//            String previousMonth = months.get(i - 1);
//
//            BigDecimal currentProfitAndLoss = monthlyProfitAndLossDifferenceMap.getOrDefault(currentMonth, BigDecimal.ZERO);
//            BigDecimal previousProfitAndLoss = monthlyProfitAndLossDifferenceMap.getOrDefault(previousMonth, BigDecimal.ZERO);
//            BigDecimal difference = currentProfitAndLoss.subtract(previousProfitAndLoss);
//            String key = previousMonth + " to " + currentMonth;
//            monthlyProfitAndLossDifference.put(key, difference);
//
//        }
//        return PortfolioRetrievals.getSortedListByMonthName(monthlyProfitAndLossDifference);
//    }
//
//    public Map<Constants, Object> getPortfolioDetails() {
//        //used to return every thing related to the portfolio
//
//        Map<Constants, Object> portfolioDetails = new HashMap<>();
//        portfolioDetails.put(TOTAL_INVESTMENT, PortfolioRetrievals.getTotalInvestment());
//        portfolioDetails.put(MONTHLY_PROFIT_AND_LOSS, getPortfolioProfitAndLoss());
//        portfolioDetails.put(MONTHLY_PROFIT_AND_LOSS_DIFFERENCE, monthlyProfitAndLossDifference());
//        portfolioDetails.put(TOTAL_STOCK_PORTFOLIO, PortfolioRetrievals.getTotalStocksPortfolio());
//        return portfolioDetails;
//    }

}
