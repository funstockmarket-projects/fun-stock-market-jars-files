package modeling.trade.constants;

import lombok.Getter;

@Getter 
public enum StockAvailability {
    UNAVALIBLE("UNAVALIBLE"), 
    AVALIBLE("AVALIBLE");

    public final String value;

    StockAvailability(String string) {
        this.value = string;
    }
}
