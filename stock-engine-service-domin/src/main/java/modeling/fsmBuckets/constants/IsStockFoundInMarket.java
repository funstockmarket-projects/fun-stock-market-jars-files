package modeling.fsmBuckets.constants;

import lombok.Getter;

@Getter
public enum IsStockFoundInMarket {
    N('n'),
    Y('y');

    private final Character isStockFoundInMarket;

    IsStockFoundInMarket(Character isStockFoundInMarket) {
        this.isStockFoundInMarket = isStockFoundInMarket;
    }
}
