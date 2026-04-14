package com.fsm.domins.marketHolidayCalender.operations;

import com.fsm.domainsMapping.businessObject.marketHolidayCalender.HolidayCalendarBO;
import com.fsm.domins.marketHolidayCalender.mapper.HolidayCalendarMapper;
import com.fsm.domins.marketHolidayCalender.models.HolidayCalendar;
import com.fsm.domins.marketHolidayCalender.repository.HolidayCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "funMarketSaveHolidayCalendar")
@RequiredArgsConstructor
public final class FunMarketSaveHolidayCalendar implements FunMarketSaveHolidayCalendarMethod {

    private static final Logger log = LoggerFactory.getLogger(FunMarketSaveHolidayCalendar.class);

    private final HolidayCalendarRepository holidayCalendarRepository;

    @Override
    public HolidayCalendarBO saveHolidayCalendar(HolidayCalendarBO holidayCalendarBO) {
        log.info("Saving holiday calendar with year: {}", holidayCalendarBO.getYear());

        HolidayCalendar holidayCalendar = HolidayCalendarMapper.bOToHolidayCalendar(holidayCalendarBO);

        try {
            holidayCalendar = holidayCalendarRepository.save(holidayCalendar);
            log.info("Successfully saved holiday calendar with [ UUID: {}, Year: {} ]", holidayCalendar.recordUuid(), holidayCalendar.year());
        } catch (Exception e) {
            log.error("Error saving holiday calendar with [ year: {} ]", holidayCalendarBO.getYear(), e);
            throw new RuntimeException("Failed to save holiday calendar", e);
        }

        return HolidayCalendarMapper.holidayCalendarToBO(holidayCalendar);
    }
}
