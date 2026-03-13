package Modules.fileValidation;

import com.fsm.domins.clearing.enums.ErrorCodes;
import lombok.Getter;

@Getter
public class FileValidationResponse {

    public boolean fileValidationResult;
    private final ErrorCodes errorCodes;

    public FileValidationResponse(boolean fileValidationResult, ErrorCodes errorCodes) {
        this.fileValidationResult = fileValidationResult;
        this.errorCodes = errorCodes;
    }
}
