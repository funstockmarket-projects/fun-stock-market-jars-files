package Modules.CommonModels.exceptions;

import com.fsm.dominsMapping.constantsBO.ErrorCodesBO;
import lombok.Getter;

@Getter
public class FileErrorContextException extends Exception{
    private String code;
    private String message;

    public FileErrorContextException(String e) {
        super(e);
    }

    public FileErrorContextException(ErrorCodesBO code){
        super(code.toString());
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
