package modeling.fsmUsers.constants;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    BLOCKED("BLOCKED");

    private final String status;

    UserStatus(String status){
        this.status=status;
    }
    
    
}
