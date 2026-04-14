package com.fsm.domins.marketHolidayCalender.operations;

import com.fsm.domainsMapping.businessObject.marketHolidayCalender.HolidayCalendarBO;
import org.springframework.stereotype.Component;

@Component(value = "funMarketSaveHolidayCalendarMethod")
public sealed interface FunMarketSaveHolidayCalendarMethod permits FunMarketSaveHolidayCalendar {

    HolidayCalendarBO saveHolidayCalendar(HolidayCalendarBO holidayCalendarBO);
}
