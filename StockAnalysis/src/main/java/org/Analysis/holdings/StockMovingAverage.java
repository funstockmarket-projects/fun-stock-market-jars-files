package org.Analysis.holdings;

//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.RecordComponent;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import Modules.CommonModels.pojo.ObjectToJsonFormat;
//import org.Analysis.priceModels.PortfolioDetails;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
public class StockMovingAverage {
//    //Calculate the average price of the stock, portfolio, etc. By Giving the name Portfolio
//    public static PortfolioDetails calculateAveragePrice(Object priceActionClass) {
//        PortfolioDetails portfolioDetails = new PortfolioDetails();
//        if (priceActionClass == null) {
//            log.warn("Price Action Class is null");
//            return portfolioDetails;
//        }
//        Class<?> clazz = priceActionClass.getClass();
//        log.info("Class Name Identified: {}", Arrays.stream(clazz.getName().split("\\.")).toList().getLast());
//        if (clazz.isRecord()) {
//            log.info("Record Class Identified: {}", Arrays.stream(clazz.getName().split("\\.")).toList().getLast());
//            for (RecordComponent rc : clazz.getRecordComponents()) {
//                try {
//                    var accessor = rc.getAccessor();
//                    Object value = accessor.invoke(priceActionClass);
//                    printFields(portfolioDetails, rc.getName(), value);
//                } catch (IllegalAccessException | InvocationTargetException e) {
//                    throw new IllegalArgumentException("Failed to access record component: " + rc.getName());
//                }
//            }
//            return returnStatementsAverageCalaculation(portfolioDetails);
//        } else {
//            log.info("Class Identified: {}", clazz.getName());
//            for (Field field : clazz.getDeclaredFields()) {
//                field.setAccessible(true);
//                try {
//                    Object value = field.get(priceActionClass);
//                    printFields(portfolioDetails, field.getName(), value);
//                } catch (IllegalAccessException e) {
//                    throw new IllegalArgumentException("Failed to access field: " + field.getName());
//                }
//            }
//            return returnStatementsAverageCalaculation(portfolioDetails);
//        }
//    }
//
//    private static PortfolioDetails returnStatementsAverageCalaculation(PortfolioDetails portfolioDetails) {
//        log.info("monthly average is calculated for: {}", portfolioDetails.getStockName());
//        log.info("Portfolio Details Calculated successful: ");
//        return portfolioDetails;
//    }
//
//
//    private static void printFields(PortfolioDetails portfolioDetails, String field, Object value) {
//        if (field.equals("StockName")) {
//            log.info("Stock Name Identified: {}", value);
//            portfolioDetails.setStockName((String) value);
//
//        }
//        if (field.equals("totalInvestment")) {
//            log.info("Total Investment Identified: {}", value);
//            portfolioDetails.setTotalInvestment((BigDecimal) value);
//        }
//        if (field.equals("monthlyPerformance")) {
//            Map<String, BigDecimal> stockPrices = (Map<String, BigDecimal>) value;
//
//            portfolioDetails.setMonthlyPerformance(stockPrices);
//            log.info("Monthly Portfolio Action Map : {}", ObjectToJsonFormat.toJsonFormat( stockPrices));
//
//            Number averagePortfolioSize = calculateAveragePriceAction(new ArrayList<>(stockPrices.values()));
//            portfolioDetails.setAveragePortfolioSize(BigDecimal.valueOf(averagePortfolioSize.doubleValue()).setScale(2, RoundingMode.HALF_UP));
//            log.info("Average Portfolio Size Calculated: {}", BigDecimal.valueOf(averagePortfolioSize.doubleValue()).setScale(2, RoundingMode.HALF_UP));
//
//            stockPrices = calculatingDifferenceInProfitAndLoss(stockPrices);
//            portfolioDetails.setMonthlyProfitAndLoss(stockPrices);
//            log.info("Monthly Profit and Loss Map Calculated: {}", ObjectToJsonFormat.toJsonFormat( stockPrices));
//
//            Number averageMonthlyProfit = calculateAveragePriceAction(new ArrayList<>(stockPrices.values()));
//            portfolioDetails.setAverageMonthlyProfit(BigDecimal.valueOf(averageMonthlyProfit.doubleValue()).setScale(2, RoundingMode.HALF_UP));
//            log.info("Average Monthly Profit Calculated: {}", BigDecimal.valueOf(averageMonthlyProfit.doubleValue()).setScale(2, RoundingMode.HALF_UP));
//        }
//        if (field.equals("stockQuantity")) {
//            Map<String, Integer> stockQuantity = (Map<String, Integer>) value;
//            portfolioDetails.setStockQuantity(PortfolioRetrievals.getSortedListByMonthName(stockQuantity));
//            log.info("Stock Quantity Map Details: {}", ObjectToJsonFormat.toJsonFormat( stockQuantity));
//        }
//    }
//
//    private static Map<String, BigDecimal> calculatingDifferenceInProfitAndLoss(Map<String, BigDecimal> profitAndLossMap) {
//        List<String> months = new ArrayList<>(profitAndLossMap.keySet());
//        Map<String, BigDecimal> profitAndLossDifferenceMap = new HashMap<>();
//
//        for (int i = 1; i < months.size(); i++) {
//            String currentMonth = months.get(i);
//            String previousMonth = months.get(i - 1);
//
//            BigDecimal currentProfitAndLoss = profitAndLossMap.getOrDefault(currentMonth, BigDecimal.ZERO);
//            BigDecimal previousProfitAndLoss = profitAndLossMap.getOrDefault(previousMonth, BigDecimal.ZERO);
//            BigDecimal difference = currentProfitAndLoss.subtract(previousProfitAndLoss);
//            String key = previousMonth + " to " + currentMonth;
//            profitAndLossDifferenceMap.put(key, difference);
//        }
//        log.info("Profit and Loss Difference. Calculated for every month: ");
//        return PortfolioRetrievals.getSortedListByMonthName(profitAndLossDifferenceMap);
//
//    }
//
//    private static Number calculateAveragePriceAction(List<? extends Number> prices) {
//        if (prices == null || prices.isEmpty()) {
//            return BigDecimal.ZERO;
//        }
//        Number average;
//        Number sum = prices.stream()
//                .map(price -> price != null ? price.intValue() : 0)
//                .reduce(0, Integer::sum);
//        average = sum.doubleValue() / prices.size();
//        log.info("Average Price Calculated: {}", BigDecimal.valueOf(average.doubleValue()).setScale(2, RoundingMode.HALF_UP));
//        return average;
//    }
}
