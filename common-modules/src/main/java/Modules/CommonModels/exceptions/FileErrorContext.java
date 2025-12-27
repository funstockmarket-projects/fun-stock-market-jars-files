package Modules.CommonModels.exceptions;

import Modules.CommonModels.enums.ErrorCodes;

public class FileErrorContext extends Exception{

    public FileErrorContext(String e) {
        super(e);
    }

    public  FileErrorContext(ErrorCodes code){
        super(code.toString());
    }


}
