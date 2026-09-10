package modeling.fsmUsers.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modeling.fsmUsers.constants.AccountType;
import modeling.fsmUsers.constants.UserActiveStatusCode;
import modeling.fsmUsers.constants.UserStatus;
import modeling.fsmUsers.constants.UserType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor 
public class FSM_UserDetailsDTO {

    private long id;
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
    private LocalDateTime accountClosingDateTime;
    private LocalDateTime recordCreatedOrModifiedDateTime;
    private String recordStatus;
}
