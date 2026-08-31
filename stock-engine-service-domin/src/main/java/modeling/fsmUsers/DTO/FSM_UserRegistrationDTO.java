package modeling.fsmUsers.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import modeling.fsmUsers.constants.UserType;

@Data
public class FSM_UserRegistrationDTO {

    @NotBlank(message = "UserName is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String userName;
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    @NotNull(message = "Mobile number is required")
    private long mobileNumber;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "user role cannot be empty")
    private UserType userRole;
}