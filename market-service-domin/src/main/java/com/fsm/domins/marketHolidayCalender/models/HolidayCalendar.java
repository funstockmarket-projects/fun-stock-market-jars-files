package com.fsm.domins.marketHolidayCalender.models;

import com.fsm.domins.globalenums.Days;
import com.fsm.domins.globalenums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Document(collection = "holiday_calendar")
public record HolidayCalendar (

    @Id
    String recordUuid,
    String year,
    String holidayAt,
    Days day,
    String description,
    LocalDate creationOrModificationDate,
    RecordStatus gitHubFileStatus,
    RecordStatus recordStatus){}
