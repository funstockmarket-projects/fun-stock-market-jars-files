package modeling.fsmUsers.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FSM_UserChangePasswordDTO {

    private long userId;
    @NotBlank(message = "Old password is required")
    @Size(min = 4, max = 10, message = "Old password must be between 4 and 10 characters")
    private String oldPassword;
    @NotBlank(message = "New password is required")
    @Size(min = 4, max = 10, message = "New password must be between 4 and 10 characters")
    private String newPassword;
    @NotBlank(message = "Conform password is required")
    @Size(min = 4, max = 10, message = "Conform password must be between 4 and 10 characters")
    private String conformPassword;
}
