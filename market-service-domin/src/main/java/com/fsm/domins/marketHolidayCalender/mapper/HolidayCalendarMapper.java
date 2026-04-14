package com.fsm.domins.marketHolidayCalender.mapper;

import com.fsm.domainsMapping.businessObject.marketHolidayCalender.HolidayCalendarBO;
import com.fsm.domainsMapping.constantsBO.DaysBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domins.marketHolidayCalender.models.HolidayCalendar;
import com.fsm.domins.globalenums.Days;
import com.fsm.domins.globalenums.RecordStatus;


public class HolidayCalendarMapper {

    public static HolidayCalendar bOToHolidayCalendar(HolidayCalendarBO bo) {
        return new HolidayCalendar(
                bo.getRecordUuid(),
                bo.getYear(),
                bo.getHolidayAt(),
                Days.valueOf(bo.getDayBO().getValue()),
                bo.getDescription(),
                bo.getCreationOrModificationDate(),
                RecordStatus.valueOf(bo.getGitHubFileStatusBO().getValue()),
                RecordStatus.valueOf(bo.getRecordStatusBO().getValue())
        );
    }

    public static HolidayCalendarBO holidayCalendarToBO(HolidayCalendar hc) {
        if (hc == null) {
            throw new IllegalArgumentException("HolidayCalendar cannot be null");
        }
        if (hc.day() == null) {
            throw new IllegalArgumentException("Day cannot be null");
        }
        if (hc.gitHubFileStatus() == null) {
            throw new IllegalArgumentException("GitHubFileStatus cannot be null");
        }
        if (hc.recordStatus() == null) {
            throw new IllegalArgumentException("RecordStatus cannot be null");
        }
        
        HolidayCalendarBO bo = new HolidayCalendarBO();
        bo.setRecordUuid(hc.recordUuid());
        bo.setYear(hc.year());
        bo.setHolidayAt(hc.holidayAt());
        bo.setDayBO(DaysBO.valueOf(hc.day().getValue()));
        bo.setDescription(hc.description());
        bo.setCreationOrModificationDate(hc.creationOrModificationDate());
        bo.setGitHubFileStatusBO(RecordStatusBO.valueOf(hc.gitHubFileStatus().getValue()));
        bo.setRecordStatusBO(RecordStatusBO.valueOf(hc.recordStatus().getValue()));
        return bo;
    }
}
