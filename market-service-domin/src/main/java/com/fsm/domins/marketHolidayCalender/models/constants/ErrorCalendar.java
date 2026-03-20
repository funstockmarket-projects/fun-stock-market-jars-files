package com.fsm.domins.marketHolidayCalender.models.constants;

import lombok.Getter;

@Getter
public enum ErrorCalendar {
    ERR_000("000", "Validation Successful"),
    ERR_001("001", "Invalid data"),
    ERR_002("002", "Holiday Exist"),
    ERR_003("003", "Invalid Year" );

    private final String calendarErrorCode;
    private final String calendarErrormessage;


    ErrorCalendar(String code, String message) {
        this.calendarErrorCode =code;
        this.calendarErrormessage=message;
    }
}
