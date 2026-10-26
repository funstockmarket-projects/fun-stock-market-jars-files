package modeling.trade.constants;

import lombok.Getter;

@Getter 
public enum OrderWindow {
    BUY("BUY"),
    SELL("SELL");

    public final String value;

    private OrderWindow(String string) {
        this.value = string;
    }
}
