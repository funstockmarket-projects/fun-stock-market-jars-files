package com.fsm.domins.marketHolidayCalender.operations;

import com.fsm.domainsMapping.businessObject.marketHolidayCalender.HolidayCalendarBO;
import org.springframework.stereotype.Component;

@Component(value = "funMarketSaveHolidayCalendarMethod")
public sealed interface FunMarketModifyHolidayCalendarMethod permits FunMarketModifyHolidayCalendar {

    HolidayCalendarBO modifyHolidayCalendar(HolidayCalendarBO holidayCalendarBO);
}
