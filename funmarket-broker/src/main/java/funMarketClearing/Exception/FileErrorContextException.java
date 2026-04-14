package funMarketClearing.Exception;

import com.fsm.domainsMapping.constantsBO.ErrorCodesBO;
import lombok.Getter;

@Getter
public class FileErrorContextException extends RuntimeException {
    private String code;
    private String message;

    public FileErrorContextException(String e) {
        super(e);
    }

    public FileErrorContextException(ErrorCodesBO code) {
        super(code.toString());
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public FileErrorContextException(String message, ErrorCodesBO code) {
        super(code.toString());
        this.code = code.getCode();
        this.message = code.getMessage()+". "+message;
    }
}