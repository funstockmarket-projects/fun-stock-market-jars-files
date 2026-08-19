package modeling.fsmBuckets.constants;

import lombok.Getter;

@Getter
public enum ExchangeStatus {
    ACTIVE("active"),
    INACTIVE("Inactive"),
    BLOCKED("blocked"),
    SUSPENDED("suspended");

    private final String exchangeStatus;

    ExchangeStatus(String exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }
}
