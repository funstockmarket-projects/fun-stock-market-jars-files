package org.Analysis.holdings;

import lombok.Getter;

@Getter
public enum Constants {

    TOTAL_INVESTMENT("totalInvestment"),
    MONTHLY_PROFIT_AND_LOSS("profitAndLoss"),
    MONTHLY_PROFIT_AND_LOSS_DIFFERENCE("monthlyProfitAndLossDifference"),
    TOTAL_STOCK_PORTFOLIO("totalStocksPortfolio");

    private final String value;

    Constants(String value) {
        this.value = value;
    }
}
