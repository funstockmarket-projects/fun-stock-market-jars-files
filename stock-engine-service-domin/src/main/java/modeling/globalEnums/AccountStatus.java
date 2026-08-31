package modeling.globalEnums;

import lombok.Getter;

@Getter
public enum AccountStatus {
    ACTIVE("ACTIVE", "111"),
    INACTIVE("INACTIVE", "11"),
    BLOCKED("BLOCKED", "0000"),
    SUSPENDED("SUSPENDED","000");

    private final String status;
    private final String code;

    AccountStatus(String active, String number) {
        status =active;
        code = number;
    }
}
