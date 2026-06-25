package modeling.fsmUsers.constants;

import lombok.Getter;

@Getter
public enum UserType {
    STOCK_HOLDER("STOCK_HOLDER"),
    ADMIN("ADMIN"),
    ADMIN_USER("ADMIN_USER"),
    DEV_TEAM("DEV_TEAM"),
    QE_TEAM("QE_TEAM");

    private final String userType;

    UserType(String userType){
        this.userType = userType;
    }
}

