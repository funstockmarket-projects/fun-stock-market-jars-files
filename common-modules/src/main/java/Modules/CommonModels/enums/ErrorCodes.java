package Modules.CommonModels.enums;

public enum ErrorCodes {

    ERR_100("1000", "INVALID FILE"),

    ERR_201("3001", "Invalid file name"),
    ERR_202("3002", "Invalid file UUID"),
    ERR_203("3003", "Invalid folder name"),
    ERR_204("3004", "Invalid file type"),
    ERR_205("3005", "Invalid file size"),
    ERR_206("3006", "Invalid number of records"),
    ERR_207("3007", "Invalid URI"),
    ERR_208("3008", "Invalid file data"),
    ERR_209("3009", "Invalid file created date"),
    ERR_210("3010", "Invalid file modified date"),
    ERR_211("3011", "File date validation failed"),
    ERR_212("3012", "Market events validation failed");

    private final String code;
    private final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
