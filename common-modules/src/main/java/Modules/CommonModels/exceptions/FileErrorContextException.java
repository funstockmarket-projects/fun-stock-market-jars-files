package Modules.CommonModels.exceptions;


import com.fsm.domins.clearing.enums.ErrorCodes;
import lombok.Getter;

@Getter
public class FileErrorContextException extends Exception{
    private String code;
    private String message;

    public FileErrorContextException(String e) {
        super(e);
    }

    public FileErrorContextException(ErrorCodes code){
        super(code.toString());
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
