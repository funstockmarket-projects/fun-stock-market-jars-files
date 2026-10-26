package modeling.dailyStockTradingBucket.enums;

import lombok.Getter;

@Getter 
public enum PriceStatus {
    UP("UP"),
    DOWN("DOWN");

    private final String getvalue;

    PriceStatus(String string) {
        getvalue = string;
    }


}
