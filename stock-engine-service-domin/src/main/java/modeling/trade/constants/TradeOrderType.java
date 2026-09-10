package modeling.trade.constants;

import lombok.Getter;

@Getter
public enum TradeOrderType {
    OBSERVABLE("OBSERVABLE"), 
    TRADE("TRADE");

    public final String value;

    TradeOrderType(String string) {
        this.value = string;
    }
}
