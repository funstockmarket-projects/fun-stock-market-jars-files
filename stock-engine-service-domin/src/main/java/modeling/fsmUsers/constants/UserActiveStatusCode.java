package modeling.fsmUsers.constants;

import lombok.Getter;

@Getter
public enum UserActiveStatusCode {
    A("A"),
    B("B"),
    P("P"),
    INA("INA");

    private final String activeStatusCode;

    UserActiveStatusCode(String activeStatusCode) {
        this.activeStatusCode = activeStatusCode;
    }
}
