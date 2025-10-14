package org.Analysis.holdings;

import Modules.CommonModels.enums.StockStatus;
import Modules.CommonModels.model.Holdings;
import Modules.CommonModels.model.StockData;
import Modules.CommonModels.model.StockPerformance;
import Modules.CommonModels.pojo.ObjectToJsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.retrieve.DataRetrieve;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PortfolioRetrievals {

    //instating the current holdings and monthly data
    public static Map<String, List<StockData>> monthlyData;
    public static Holdings monthlyPerformance;

    static {
        //retrieving the holdings data from the DataRetrieve class and Storing it in the currentHoldings and monthlyData maps

        monthlyPerformance = DataRetrieve.monthlyPerformance;

        monthlyData = monthlyPerformance.getStockData().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, value -> {
                    return value.getValue().getMonthlyData().values().stream().toList();
                }));
    }

    protected static Map<String, StockData> getCurretHoldins() {
        //This method is used for getting the current holdings with the last month data
        return monthlyPerformance.getStockData().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, value -> {
                    return value.getValue()
                            .getMonthlyData()
                            .getOrDefault(lastMonthFileName(), new StockData("", 0, 0, 0, 0, 0, 0, 0, StockStatus.SOLD));
                }));
    }

    public static String lastMonthFileName() {
        //This method is used for getting the last fileName
        List<String> stockData = monthlyPerformance.getStockData().values().stream()
                .map(e -> {
                    return e.getMonthlyData().keySet().stream().toList();
                }).flatMap(Collection::stream).toList();

        String heightFileNumber = stockData.stream()
                .distinct().map(fileNumber -> fileNumber.split(" ")[0].replace("\\D", ""))
                .map(Integer::parseInt).distinct().min((a, b) -> b - a).orElse(0).toString();

        return monthlyPerformance.getStockData().values().stream().map(e -> {
                    return e.getMonthlyData()
                            .keySet()
                            .stream()
                            .toList();
                }).flatMap(Collection::stream)
                .filter(fileName -> fileName.startsWith(heightFileNumber))
                .findFirst()
                .orElse(null);
    }

    public LinkedHashMap<String, BigDecimal> getPortfolioOfEveryMonthProfitAndLoss() {
        //This method is used for getting Portfolio( profit and loss) of every month

        // Get all months to retrieve
        List<String> months = monthlyPerformance.getStockData().values().stream()
                .map(e -> {
                    return e.getMonthlyData().keySet().stream().toList();
                }).flatMap(Collection::stream).distinct().toList();
        // Initialize a map to hold the portfolio values for each month
        Map<String, BigDecimal> portfolio = new HashMap<>();

        // retrieve the stock data by month name
        for (String month : months) {

            BigDecimal totalValue = monthlyPerformance.getStockData().values().stream()
                    .map(e -> e.getMonthlyData().getOrDefault(month, new StockData("", 0, 0, 0, 0, 0, 0, 0, StockStatus.SOLD)))
                    .map(StockData::currentValue)
                    .map(BigDecimal::new)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_EVEN);
            portfolio.put(month, totalValue);
        }
        //returning the portfolio sorted by month
        return getSortedListByMonthName(portfolio);
    }

    protected static Map<String, StockData> getStockByMonthName(String monthName) {
        //This method is used for getting the portfolio by month name
        // Get all stocks for the given month
        return monthlyPerformance.getStockData().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, value -> {
                    return value.getValue().getMonthlyData().getOrDefault(monthName, new StockData("", 0, 0, 0, 0, 0, 0, 0, StockStatus.SOLD));
                }));
    }

    protected static BigDecimal getTotalInvestment() {
        //this method return the total investment in the portfolio
        return getCurretHoldins().values().stream()
                .map(n -> BigDecimal.valueOf(n.quantity()).multiply(BigDecimal.valueOf(n.averageCost())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    protected static LinkedHashMap<String, BigDecimal> getInvestmentOfEveryMonth() {
        List<String> months = monthlyPerformance.getStockData().values().stream()
                .map(e -> {
                    return e.getMonthlyData().keySet().stream().toList();
                }).flatMap(Collection::stream).distinct().toList();
        // Initialize a map to hold the portfolio values for each month
        Map<String, BigDecimal> portfolio = new HashMap<>();

        // retrieve the stock data by month name
        for (String month : months) {

            BigDecimal totalValue = monthlyPerformance.getStockData().values().stream()
                    .map(e -> e.getMonthlyData().getOrDefault(month, new StockData("", 0, 0, 0, 0, 0, 0, 0, StockStatus.SOLD)))
                    .map(stockData -> {
                        return BigDecimal.valueOf(stockData.quantity()).multiply(BigDecimal.valueOf(stockData.averageCost()));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_EVEN);
            portfolio.put(month, totalValue);
        }

        //returning the portfolio sorted by month
        return getSortedListByMonthName(portfolio);

    }

    protected static <T> LinkedHashMap<String, T> getSortedListByMonthName(Map<String, T> portfolio) {
        //this method is used to sort the portfolio by month name
        return portfolio.entrySet().stream()
                .sorted((a, b) ->
                        Integer.parseInt(a.getKey().split(" ")[0].replaceAll("\\D", "")) -
                                Integer.parseInt(b.getKey().split(" ")[0].replaceAll("\\D", "")))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)
                );
    }

    protected static List<StockPerformance> getTotalStocksPortfolio() {
        List<StockPerformance> stockPerformances = new ArrayList<>();
        monthlyPerformance.getStockData().keySet()
                .stream().distinct()
                .toList()
                .forEach(stockName -> {
                    StockData lastMonthData = getStockByMonthName(lastMonthFileName())
                            .getOrDefault(stockName, new StockData("", 0, 0, 0, 0, 0, 0, 0, StockStatus.SOLD));
                    BigDecimal currentValue = BigDecimal.valueOf(lastMonthData.averageCost());
                    BigDecimal quantity = BigDecimal.valueOf(lastMonthData.quantity());
                    StockPerformance stockPerformance = new StockPerformance(
                            stockName,
                            getSortedListByMonthName(getQuantityByMonth(stockName)),
                            currentValue.multiply(quantity).setScale(2, RoundingMode.HALF_EVEN),
                            getSortedListByMonthName(getYearCurrentValue(stockName))
                    );
                    stockPerformances.add(stockPerformance);
                })
        ;
        return stockPerformances;
    }

    protected static Map<String, String> getQuantityByMonth(String stockName) {
        Map<String, String> quantityByMonth = new HashMap<>();
        Map<String, String> rawData = monthlyPerformance.getStockData().get(stockName).getMonthlyData()
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    StockData stockData = entry.getValue();
                    return String.valueOf(stockData.quantity());
                }));
        String previousQuantity = "0";
        for (String month : getSortedListByMonthName(rawData).keySet()) {
            String quantity = rawData.get(month);
            if (!previousQuantity.equals(quantity)) {
                int qty = Integer.parseInt(quantity);
                int prevQty = Integer.parseInt(previousQuantity);
                if (qty - prevQty != 0) {
                    String result = "";
                    if(qty>prevQty){
                         result = (qty - prevQty)+ " Stock Added. Total: "+qty;
                    }else{
                        result = (prevQty - qty)+" Sold Total: "+qty;
                    }
                    quantityByMonth.put(month, result);
                }// 3-3=0
                previousQuantity = String.valueOf(qty);

            }
        }
        return quantityByMonth;
    }

    protected static Map<String, BigDecimal> getYearCurrentValue(String stockName) {
        return monthlyPerformance.getStockData().get(stockName).getMonthlyData()
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    StockData stockData = entry.getValue();
                    return BigDecimal.valueOf(stockData.currentValue());
                }));
    }

    public static StockPerformance getStockDataByName(String stockName) {
        stockName = stockName.trim().toUpperCase();
        String finalStockName = stockName;
        log.info("Searching for: {}", stockName);
        StockPerformance stockPerformance = getTotalStocksPortfolio().stream()
                .filter(stockDetails -> stockDetails.StockName().equals(finalStockName))
                .findFirst()
                .orElse(new StockPerformance(null, Collections.emptyMap(), BigDecimal.ZERO, Collections.emptyMap()));

        if (stockPerformance.StockName().isBlank()) {
            log.warn("Stock not found: {}", stockName);
        } else {
            log.info("Stock found: {}", stockName);
            log.info("{} Details {}", ObjectToJsonFormat.toJsonFormat(stockName), ObjectToJsonFormat.toJsonFormat(stockPerformance));
        }
        return stockPerformance;
    }
}