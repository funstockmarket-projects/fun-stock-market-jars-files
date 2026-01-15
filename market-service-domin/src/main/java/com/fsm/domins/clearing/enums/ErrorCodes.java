package com.fsm.domins.clearing.enums;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    ERR_100("1000", "INVALID FILE"),

    ERR_3001("3001", "Invalid file name"),
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
    ERR_3012("3012", "Market events name invalid"),;

    private final String code;
    private final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
