package com.fsm.domins.clearing.enums;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    ERR_1000("1000", "INVALID FILE"),
    ERR_1001("1001", "Invalid file name"),
    ERR_1002("1002", "File name pattern validation failed"),
    ERR_1003("1003", "Invalid File Period"),

    ERR_3002("3002", "Invalid file UUID"),
    ERR_3003("3003", "Invalid folder name"),
    ERR_3004("3004", "Invalid file type"),
    ERR_3005("3005", "Invalid file size"),
    ERR_3006("3006", "Invalid number of records"),
    ERR_3007("3007", "Invalid URI"),
    ERR_3008("3008", "Invalid file data"),
    ERR_3009("3009", "Invalid file created date"),
    ERR_3010("3010", "Invalid file modified date"),
    ERR_3011("3011", "File date validation failed"),
    ERR_3012("3012", "Market events name invalid"),
    ERR_0000("0000", "Unknown error"),
    ERR_0001("0001", "File processing failed"),
    ERR_0002("0002", "File Details Modified"),
    ERR_5001("5001", "File name exist"),

    ERR_100("100", "No matching record"),
    ERR_101("101", "File UUID not matched"),
    ERR_102("102", "File name not matched"),
    ERR_103("103", "Created date not matched"),

    ERR_301("301", "Uploaded holiday file"),
    ERR_302("302", "Uploaded weekend file"),

    ERR_401("401", "File Folder Not Match"),

    ERR_501("501", "Invalid Year"),
    ERR_502("502", "Duplicate FileName");


    private final String code;
    private final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
