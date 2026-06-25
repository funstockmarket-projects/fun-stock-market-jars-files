package modeling.auth.brokerTokenEntity.enums;

import lombok.Getter;

@Getter
public enum TokenStatus {
    ACTIVE("ACTIVE"),
    EXPIRED("EXPIRED");

    private final String value;

    TokenStatus(String value){
        this.value=value;
    }
}
