package modeling.trade.constants;

import lombok.Getter;

@Getter 
public enum OrderStatus {
    SETTLED("SETTLED"), 
    PENDING("PENDING"), 
    CANCELLED("CANCELLED"), 
    FAILED("FAILED"), 
    UNKNOWN("UNKNOWN"), 
    INVALID("INVALID");

    public final String value;

    OrderStatus(String string) {
        this.value = string;
    }
}
