package funMarketMarketHolidayCalender.operations;

import com.fsm.domins.marketHolidayCalender.models.constants.ErrorCalendar;
import com.fsm.domins.marketHolidayCalender.operations.FunMarketHolidayCalendarRetrievalMethods;
import com.fsm.domins.marketHolidayCalender.operations.FunMarketSaveHolidayCalendarMethod;
import com.fsm.domins.marketHolidayCalender.validationService.CalendarValidationService;
import com.fsm.domainsMapping.businessObject.marketHolidayCalender.HolidayCalendarBO;
import com.fsm.domainsMapping.constantsBO.DaysBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service("holidayCalendarServiceEng")
public class HolidayCalendarServiceEng {


    private final static CalendarValidationService calendarValidationService = new CalendarValidationService();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);

    private final FunMarketHolidayCalendarRetrievalMethods funMarketHolidayCalendarRetrievalMethods;
    private final FunMarketSaveHolidayCalendarMethod funMarketSaveHolidayCalendarMethod;


    public HolidayCalendarServiceEng(@Qualifier(value = "funMarketHolidayCalendarRetrievals") FunMarketHolidayCalendarRetrievalMethods funMarketHolidayCalendarRetrievalMethods,
                                     @Qualifier(value = "funMarketSaveHolidayCalendar") FunMarketSaveHolidayCalendarMethod funMarketSaveHolidayCalendarMethod) {
        this.funMarketHolidayCalendarRetrievalMethods = funMarketHolidayCalendarRetrievalMethods;
        this.funMarketSaveHolidayCalendarMethod = funMarketSaveHolidayCalendarMethod;
    }


    public List<HolidayCalendarBO> saveHolidayCalender(List<Map<String, String>> calendar) {

        if (calendar == null || calendar.isEmpty()) {
            log.warn("Received empty or null calendar data");
            return Collections.emptyList();
        }

        log.info("Processing {} holiday records", calendar.size());

        List<HolidayCalendarBO> existingHolidays = funMarketHolidayCalendarRetrievalMethods.findAll();

        List<HolidayCalendarBO> savedList = new ArrayList<>();

        for (Map<String, String> holiday : calendar) {
            String year = holiday.getOrDefault("year", "").trim();
            String date = holiday.getOrDefault("holidayAt", "").trim();
            String day = holiday.getOrDefault("day", "").trim();
            String description = holiday.getOrDefault("description", "").trim();
            String githubStatus = holiday.getOrDefault("gitHubFileStatus", "").trim();

            Optional<HolidayCalendarBO> optionalHoliday =
                    buildHolidayCalendar(date, day, description, githubStatus);

            if (optionalHoliday.isEmpty()) {
                log.warn("Skipping invalid date format: {}", date);
                log.warn("Failed to add the Holiday  FileName {} Holiday: {}", year, date);
                continue;
            }

            HolidayCalendarBO holidayCalendar = optionalHoliday.get();

            ErrorCalendar error = calendarValidationService
                    .validateHolidayWithExistingHolidays(existingHolidays, holidayCalendar);

            String errorCode = error.getCalendarErrorCode();
            String yearFromDate = holidayCalendar.getYear();

            if (!yearFromDate.equals(year)) {
                log.error("The Holiday from the different file, holiday: {}, year {}, fileName: {}", date, yearFromDate, year);
                continue;
            }

            if (ErrorCalendar.ERR_002.getCalendarErrorCode().equals(errorCode)) {

                log.warn("Validation status errorCode: {}, ErrorMessage: {}", error.getCalendarErrorCode(), error.getCalendarErrormessage());

                Optional<HolidayCalendarBO> existing = existingHolidays.stream()
                        .filter(h -> yearFromDate.equals(h.getYear()))
                        .filter(h -> date.equals(h.getHolidayAt()))
                        .findFirst();

                HolidayCalendarBO toUpdate = existing.orElseGet(() -> {
                    log.warn("Holiday record not found for update. Creating new record for year: {}, date: {}", yearFromDate, date);
                    return new HolidayCalendarBO();
                });

                toUpdate.setYear(yearFromDate);
                toUpdate.setHolidayAt(date);
                toUpdate.setDescription(description);
                toUpdate.setCreationOrModificationDate(LocalDate.now());
                toUpdate.setDayBO(parseDay(day));
                toUpdate.setGitHubFileStatusBO(recordStatus(githubStatus));
                toUpdate.setRecordStatusBO(RecordStatusBO.MODIFIED);

                log.info("Holiday Modifying the Holiday. year: {}, Date; {}, Description: {}", yearFromDate, date, description);
                holidayCalendar = this.saveHoliday(toUpdate);
            } else if (ErrorCalendar.ERR_000.getCalendarErrorCode().equals(errorCode)) {
                log.info("Trying to save Holiday Record.  year: {}, Date; {}, Description: {}", yearFromDate, date, description);
                holidayCalendar = this.saveHoliday(holidayCalendar);
            }
            savedList.add(holidayCalendar);
        }
        return savedList;
    }

    public HolidayCalendarBO saveHoliday(HolidayCalendarBO holidayCalendarBO) {

        if (holidayCalendarBO == null) {
            log.info("No Holiday input returning null");
            return null;
        }
        log.info("Saving Holiday Record");
        return this.funMarketSaveHolidayCalendarMethod.saveHolidayCalendar(holidayCalendarBO);
    }

    public List<HolidayCalendarBO> findByYear(String year) {
        return funMarketHolidayCalendarRetrievalMethods.findAll().stream()
                .filter(exYear -> exYear.getYear().equals(year))
                .toList();
    }

    public List<HolidayCalendarBO> findByHolidayAt(String date) {
        return funMarketHolidayCalendarRetrievalMethods.findAll().stream()
                .filter(h -> date.equals(h.getHolidayAt()))
                .toList();
    }

    private Optional<HolidayCalendarBO> buildHolidayCalendar(String date, String day, String description, String githubStatus) {

        try {
            LocalDate parsedDate = LocalDate.parse(date, FORMATTER);
            String year = String.valueOf(parsedDate.getYear());

            return Optional.of(
                    HolidayCalendarBO.builder()
                            .recordUuid(UUID.randomUUID().toString())
                            .year(year)
                            .dayBO(parseDay(day))
                            .holidayAt(date)
                            .description(description)
                            .creationOrModificationDate(LocalDate.now())
                            .gitHubFileStatusBO(recordStatus(githubStatus))
                            .recordStatusBO(RecordStatusBO.ADDED)
                            .build()
            );

        } catch (Exception e) {
            return Optional.empty(); // clean handling
        }
    }

    private DaysBO parseDay(String day) {
        try {
            return DaysBO.valueOf(day.toUpperCase());
        } catch (Exception e) {
            log.warn("Invalid day value: {}", day);
            return null; // or default
        }
    }

    private RecordStatusBO recordStatus(String status) {
        try {
            return RecordStatusBO.valueOf(status.toUpperCase());
        } catch (Exception e) {
            log.warn("Invalid Record Status value: {}", status);
            return null; // or default
        }
    }

    public List<HolidayCalendarBO> findAll(){
        try{
            return funMarketHolidayCalendarRetrievalMethods.findAll();
        }catch (Exception e){
            log.error("Error retrieving all holiday records: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}