package Modules.fileValidation;


import com.fsm.dominsMapping.constantsBO.ErrorCodesBO;
import lombok.Getter;

@Getter
public class FileValidationResponse {

    public boolean fileValidationResult;
    private final ErrorCodesBO errorCodes;

    public FileValidationResponse(boolean fileValidationResult, ErrorCodesBO errorCodes) {
        this.fileValidationResult = fileValidationResult;
        this.errorCodes = errorCodes;
    }
}
