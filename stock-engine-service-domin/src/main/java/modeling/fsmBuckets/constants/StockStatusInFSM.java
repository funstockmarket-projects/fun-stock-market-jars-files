package modeling.fsmBuckets.constants;

import lombok.Getter;

@Getter
public enum StockStatusInFSM {
    ACTIVE("active"),
    INACTIVE("inactive"),
    BLOCKED("blocked"),
    SUSPENDED("suspended");

    private final String stockStatusInFSM;

    StockStatusInFSM(String stockStatusInFSM) {
        this.stockStatusInFSM = stockStatusInFSM;
    }
}
