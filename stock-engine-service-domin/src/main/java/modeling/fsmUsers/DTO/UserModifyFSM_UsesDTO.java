package modeling.fsmUsers.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModifyFSM_UsesDTO {

    private long userId;
    private String funmarketPolicyId;
    @NotBlank(message = "UserName is required")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    private String userName;
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    @NotNull(message = "Mobile number is required")
    private Long mobileNumber;
}
