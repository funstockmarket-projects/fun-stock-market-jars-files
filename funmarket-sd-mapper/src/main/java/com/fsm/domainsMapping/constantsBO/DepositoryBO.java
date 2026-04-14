package com.fsm.domainsMapping.constantsBO;

import lombok.Getter;

@Getter
public enum DepositoryBO {
    CDSL("555", "CDSL"),
    NSDL("666", "NSDL");

    DepositoryBO(String code, String depository ){
        this.code = code;
        this.depository = depository;
    }

    public final String code;
    public final String depository;
}

