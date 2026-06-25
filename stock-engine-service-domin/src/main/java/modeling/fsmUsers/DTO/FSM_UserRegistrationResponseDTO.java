package modeling.fsmUsers.DTO;

import lombok.Data;
import modeling.fsmUsers.constants.AccountType;
import modeling.fsmUsers.constants.UserActiveStatusCode;
import modeling.fsmUsers.constants.UserStatus;
import modeling.fsmUsers.constants.UserType;

import java.time.LocalDateTime;

@Data
public class FSM_UserRegistrationResponseDTO {
    private Long userId;
    private String userUuid;
    private String funmarketPolicyId;
    private String userName;
    private String email;
    private Long mobileNumber;
    private UserStatus userStatus;
    private UserType userType;
    private AccountType accountType;
    private UserActiveStatusCode userActiveStatusCode;
    private LocalDateTime accountOpeningTime;
}
