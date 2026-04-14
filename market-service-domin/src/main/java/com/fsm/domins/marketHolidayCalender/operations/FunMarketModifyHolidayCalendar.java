package com.fsm.domins.marketHolidayCalender.operations;

import com.fsm.domainsMapping.businessObject.marketHolidayCalender.HolidayCalendarBO;
import com.fsm.domins.marketHolidayCalender.mapper.HolidayCalendarMapper;
import com.fsm.domins.marketHolidayCalender.models.HolidayCalendar;
import com.fsm.domins.marketHolidayCalender.repository.HolidayCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "funMarketModifyHolidayCalendar")
@RequiredArgsConstructor
public final class FunMarketModifyHolidayCalendar implements FunMarketModifyHolidayCalendarMethod {

    private static final Logger log = LoggerFactory.getLogger(FunMarketModifyHolidayCalendar.class);

    private final HolidayCalendarRepository holidayCalendarRepository;

    @Override
    public HolidayCalendarBO modifyHolidayCalendar(HolidayCalendarBO holidayCalendarBO) {
        log.info("Modifying holiday calendar with UUID: {}", holidayCalendarBO.getRecordUuid());

        HolidayCalendar holidayCalendar = HolidayCalendarMapper.bOToHolidayCalendar(holidayCalendarBO);

        try {
            holidayCalendar = holidayCalendarRepository.save(holidayCalendar);
            log.info("Successfully modified holiday calendar with [ UUID: {}, Year: {} ]", holidayCalendar.recordUuid(), holidayCalendar.year());
        } catch (Exception e) {
            log.error("Error modifying holiday calendar with [ UUID: {} ]", holidayCalendarBO.getRecordUuid(), e);
            throw new RuntimeException("Failed to modify holiday calendar", e);
        }

        return HolidayCalendarMapper.holidayCalendarToBO(holidayCalendar);
    }
}
