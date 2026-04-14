package com.fsm.domins.marketHolidayCalender.operations;

import com.fsm.domainsMapping.businessObject.marketHolidayCalender.HolidayCalendarBO;
import com.fsm.domins.globalenums.Days;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "funMarketHolidayCalendarRetrievalMethods")
public sealed interface FunMarketHolidayCalendarRetrievalMethods permits FunMarketHolidayCalendarRetrievals {

    HolidayCalendarBO findByRecordUuid(String recordUuid);

    List<HolidayCalendarBO> findByYear(String year);

    List<HolidayCalendarBO> findByDay(Days day);

    List<HolidayCalendarBO> findAll();
}
