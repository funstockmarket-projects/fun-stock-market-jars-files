package modeling.fsmUsers.constants;

import lombok.Getter;

@Getter
public enum AccountType {
    FREE_USER("FREE_USER"),
    PREMIUM_USER("PREMIUM_USER"),
    ADMIN("ADMIN"),
    ADMIN_USER("ADMIN_USER"),
    VIP("VIP"),
    CUSTOMER("CUSTOMER");

    private final String accountType;

     AccountType(String type){
        this.accountType = type;
    }
}
