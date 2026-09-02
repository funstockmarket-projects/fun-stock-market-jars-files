package modeling.globalEnums;

import lombok.Getter;

@Getter
public enum IsStockFoundInMarket {
    N('N'),
    Y('Y');

    private final Character isStockFoundInMarket;

    IsStockFoundInMarket(Character isStockFoundInMarket) {
        this.isStockFoundInMarket = isStockFoundInMarket;
    }
}
