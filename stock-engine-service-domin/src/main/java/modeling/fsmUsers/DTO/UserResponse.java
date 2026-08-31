package modeling.fsmUsers.DTO;

import modeling.fsmUsers.constants.AccountType;
import modeling.fsmUsers.constants.UserActiveStatusCode;

public class UserResponse {
    public String userName;
    public String email;
    public AccountType accountType;
    public UserActiveStatusCode userActiveStatusCode;
}
