package modeling.FsmHoldings.constants;

import lombok.Getter;

@Getter
public enum TypeOfHoldingsAccount {

    LARGER_ACCOUNT("LARGER_ACCOUNT"),
    NORMAL_ACCOUNT("NORMAL_ACCOUNT"),
    OBSERVABLE_ACCOUNT("OBSERVABLE_ACCOUNT"),
    NORMAL_OBSERVABLE("NORMAL_OBSERVABLE"),
    LARGER_OBSERVABLE("LARGER_OBSERVABLE");

    private final String holdingsType;

    TypeOfHoldingsAccount(String type) {
        holdingsType=type;
    }
}
