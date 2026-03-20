package com.fsm.domins.marketHolidayCalender.repository;

import com.fsm.domins.globalenums.Days;
import com.fsm.domins.marketHolidayCalender.models.HolidayCalendar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayCalendarRepository extends MongoRepository<HolidayCalendar, String> {

    List<HolidayCalendar> findByYear(String year);

    List<HolidayCalendar> findByDay(Days day);

}