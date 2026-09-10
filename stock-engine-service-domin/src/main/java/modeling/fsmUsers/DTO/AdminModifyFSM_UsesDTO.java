package modeling.fsmUsers.DTO;

import lombok.Data;
import modeling.fsmUsers.constants.AccountType;
import modeling.fsmUsers.constants.UserActiveStatusCode;
import modeling.fsmUsers.constants.UserStatus;
import modeling.fsmUsers.constants.UserType;

@Data
public class AdminModifyFSM_UsesDTO {
    private long userId;
    private UserStatus userStatus;
    private UserType userType;
    private AccountType accountType;
    private UserActiveStatusCode userActiveStatusCode;
}
