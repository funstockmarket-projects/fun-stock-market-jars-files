package modeling.fsmBuckets.constants;

import lombok.Getter;

@Getter
public enum IsStockDelistedMarket {
    LISTED("LISTED"),
    DLISTED("DLISTED"),
    SUSPENDED("SUSPENDED");

    private final String isStockDelistedMarket;

    IsStockDelistedMarket(String isStockDelistedMarket) {
        this.isStockDelistedMarket = isStockDelistedMarket;
    }
}
