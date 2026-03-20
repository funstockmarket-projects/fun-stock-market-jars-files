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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "holiday_calendar")
public class HolidayCalendar {

    @Id
    private String recordUuid;

    private String year;

    private String holidayAt;

    private Days day;

    private String description;

    private LocalDate creationOrModificationDate;

    private RecordStatus gitHubFileStatus;

    private RecordStatus recordStatus;

}
