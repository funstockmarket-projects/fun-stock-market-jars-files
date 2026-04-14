package com.fsm.domainsMapping.businessObject.marketHolidayCalender;

import com.fsm.domainsMapping.constantsBO.DaysBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HolidayCalendarBO {

    private String recordUuid;
    private String year;
    private String holidayAt;
    private DaysBO dayBO;
    private String description;
    private LocalDate creationOrModificationDate;
    private RecordStatusBO gitHubFileStatusBO;
    private RecordStatusBO recordStatusBO;

}
