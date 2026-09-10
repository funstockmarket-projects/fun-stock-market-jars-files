package modeling.globalEnums;

import lombok.Getter;

@Getter
public enum StockStatusInMarket {
    LISTED("LISTED"),
    DLISTED("DLISTED"),
    SUSPENDED("SUSPENDED");

    private final String isStockDelistedMarket;

    StockStatusInMarket(String isStockDelistedMarket) {
        this.isStockDelistedMarket = isStockDelistedMarket;
    }
}
