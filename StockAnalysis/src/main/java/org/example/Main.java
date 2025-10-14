package org.example;


import Modules.CommonModels.model.StockPerformance;
import org.Analysis.holdings.PortfolioRetrievals;
import org.Analysis.holdings.StockMovingAverage;
import org.Analysis.priceModels.PortfolioDetails;

public class Main {
    public static void main(String[] args) {

        StockPerformance stockPerformance = PortfolioRetrievals.getStockDataByName("VIKASLIFE");
        StockMovingAverage.calculateAveragePrice(stockPerformance);
    }
}