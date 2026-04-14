package com.fsm.domins.marketHolidayCalender.operations;

import com.fsm.domainsMapping.businessObject.marketHolidayCalender.HolidayCalendarBO;
import com.fsm.domins.globalenums.Days;
import com.fsm.domins.marketHolidayCalender.mapper.HolidayCalendarMapper;
import com.fsm.domins.marketHolidayCalender.models.HolidayCalendar;
import com.fsm.domins.marketHolidayCalender.repository.HolidayCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "funMarketHolidayCalendarRetrievals")
@RequiredArgsConstructor
public final class FunMarketHolidayCalendarRetrievals implements FunMarketHolidayCalendarRetrievalMethods {

    private final HolidayCalendarRepository holidayCalendarRepository;

    @Override
    public HolidayCalendarBO findByRecordUuid(String recordUuid) {
        Optional<HolidayCalendar> holidayCalendar = holidayCalendarRepository.findById(recordUuid);
        return holidayCalendar.map(HolidayCalendarMapper::holidayCalendarToBO).orElse(null);
    }

    @Override
    public List<HolidayCalendarBO> findByYear(String year) {
        List<HolidayCalendar> holidayCalendars = holidayCalendarRepository.findByYear(year);
        return holidayCalendars.stream().map(HolidayCalendarMapper::holidayCalendarToBO).toList();
    }

    @Override
    public List<HolidayCalendarBO> findByDay(Days day) {
        List<HolidayCalendar> holidayCalendars = holidayCalendarRepository.findByDay(day);
        return holidayCalendars.stream().map(HolidayCalendarMapper::holidayCalendarToBO).toList();
    }

    @Override
    public List<HolidayCalendarBO> findAll() {
        List<HolidayCalendar> holidayCalendars = holidayCalendarRepository.findAll();
        return holidayCalendars.stream().map(HolidayCalendarMapper::holidayCalendarToBO).toList();
    }
}
