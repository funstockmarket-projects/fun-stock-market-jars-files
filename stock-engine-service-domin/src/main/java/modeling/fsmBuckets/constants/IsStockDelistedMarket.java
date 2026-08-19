package modeling.fsmBuckets.constants;

import lombok.Getter;

@Getter
public enum IsStockDelistedMarket {
    LISTED("listed"),
    DLISTED("dlisted"),
    SUSPENDED("suspended");

    private final String isStockDelistedMarket;

    IsStockDelistedMarket(String isStockDelistedMarket) {
        this.isStockDelistedMarket = isStockDelistedMarket;
    }
}
