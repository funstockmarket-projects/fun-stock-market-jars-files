package com.fsm.domins.marketHolidayCalender.validationService;

import com.fsm.domins.marketHolidayCalender.models.HolidayCalendar;
import com.fsm.domins.marketHolidayCalender.models.constants.ErrorCalendar;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import static com.fsm.domins.marketHolidayCalender.models.constants.ErrorCalendar.*;

@Slf4j
public class CalendarValidationService {

    public ErrorCalendar validateHolidayWithExistingHolidays(List<HolidayCalendar> holidayCalendarList, HolidayCalendar holidayCalendar){

        String year = holidayCalendar.getYear();
        String holidayAt = holidayCalendar.getHolidayAt();
        String day = holidayCalendar.getDay().getValue();
        String description = holidayCalendar.getDescription();

        if(year.isBlank() || holidayAt.isBlank() || day.isBlank() || description.isBlank()){
            log.error("Invalid Date: {}", holidayCalendar);
            return ERR_001;
        }
        if(Integer.parseInt(year) > LocalDate.now().getYear()){
            log.error("Invalid year can't grater than the present year. Year :{}", year);
            return ERR_003;
        }

        boolean checkingWithExistingDate = holidayCalendarList.stream()
                .filter(exi -> exi.getYear().equals(year))
                .filter(exiDate -> exiDate.getHolidayAt().equals(holidayAt))
                .toList().isEmpty();

        if(!checkingWithExistingDate){
            return ERR_002;
        }
        return ERR_000;
    }
}
