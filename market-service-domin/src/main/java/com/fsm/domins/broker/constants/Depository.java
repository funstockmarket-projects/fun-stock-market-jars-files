package com.fsm.domins.broker.constants;

import lombok.Getter;

@Getter
public enum Depository {
    CDSL("555", "CDSL"),
    NSDL("666", "NSDL");

    Depository(String code, String depository ){
        this.code = code;
        this.depository = depository;
    }

    public final String code;
    public final String depository;
}
