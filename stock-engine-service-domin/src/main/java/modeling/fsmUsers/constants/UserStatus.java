package modeling.fsmUsers.constants;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("IN-ACTIVE"),
    BLOCKED("BLOCKED");

    private final String status;

    UserStatus(String status){
        this.status=status;
    }
    
    
}
