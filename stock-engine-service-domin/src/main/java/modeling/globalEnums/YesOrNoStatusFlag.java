package modeling.globalEnums;

import lombok.Getter;

@Getter
public enum YesOrNoStatusFlag {
    N('N'),
    Y('Y');

    private final Character isStockFoundInMarket;

    YesOrNoStatusFlag(Character isStockFoundInMarket) {
        this.isStockFoundInMarket = isStockFoundInMarket;
    }
}
