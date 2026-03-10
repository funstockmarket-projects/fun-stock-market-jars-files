package Modules.fileValidation;

import com.fsm.domins.clearing.enums.ErrorCodes;
import lombok.extern.slf4j.Slf4j;

import java.time.Month;
import java.time.YearMonth;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class FileNameValidation {

    protected String fileName;

    private static final String FILE_NAME_REGEX = "^\\d+\\s\\d+\\s\\d+_(day|week|month|year)\\s([A-Za-z]{3}|[A-Za-z]{4,9})$";

    private static final int SERIAL_NUMBER_INDEX = 0;
    private static final int YEAR_INDEX = 1;
    private static final int TIME_PERIOD_INDEX = 2;
    private static final int EVENT_NAME_INDEX = 3;
    private static final int MONTH_NAME_INDEX = 4;

    private static final int MIN_YEAR = 1900;
    private static final int MIN_VALID_SERIAL = 1;

    private enum EventType {
        DAY("day", 1, 31),
        WEEK("week", 1, 5),
        MONTH("month", 1, 12),
        YEAR("year", 1, 1);

        private final String value;
        private final int minPeriod;
        private final int maxPeriod;

        EventType(String value, int minPeriod, int maxPeriod) {
            this.value = value;
            this.minPeriod = minPeriod;
            this.maxPeriod = maxPeriod;
        }

        public static Optional<EventType> fromString(String value) {
            return Arrays.stream(EventType.values())
                    .filter(e -> e.value.equalsIgnoreCase(value))
                    .findFirst();
        }

        public boolean isValidPeriod(int period) {
            return period >= minPeriod && period <= maxPeriod;
        }
    }

    private static final Set<String> VALID_MONTHS_SHORT = Set.of(
            "jan", "feb", "mar", "apr", "may", "jun",
            "jul", "aug", "sep", "oct", "nov", "dec");

    private static final Set<String> VALID_MONTHS_FULL = Set.of(
            "january", "february", "march", "april", "may", "june",
            "july", "august", "september", "october", "november", "december");

    private static final Map<String, String> SHORT_TO_FULL_MONTH = Map.ofEntries(
            Map.entry("jan", "january"),
            Map.entry("feb", "february"),
            Map.entry("mar", "march"),
            Map.entry("apr", "april"),
            Map.entry("may", "may"),
            Map.entry("jun", "june"),
            Map.entry("jul", "july"),
            Map.entry("aug", "august"),
            Map.entry("sep", "september"),
            Map.entry("oct", "october"),
            Map.entry("nov", "november"),
            Map.entry("dec", "december")
    );

    public FileNameValidation(String fileName) {
        this.fileName = fileName;
    }

    public FileValidationResponse processFileName() {
        log.info("Starting file name validation for file name: {}", fileName);

        if (!isFileNameNotEmpty()) {
            return invalidResponse(ErrorCodes.ERR_1001, "File name is empty or null");
        }
        if (!isPatternValid()) {
            return invalidResponse(ErrorCodes.ERR_1002, "File name pattern validation failed");
        }
        if (!isTimePeriodValid()) {
            return invalidResponse(ErrorCodes.ERR_1003, "File time period validation failed");
        }
        return new FileValidationResponse(true, null);
    }

    private boolean isFileNameNotEmpty() {
        return fileName != null && !fileName.isEmpty();
    }

    private boolean isPatternValid() {
        boolean isValid = Pattern.matches(FILE_NAME_REGEX, fileName);
        if (!isValid) {
            log.error("File name pattern does not match required format: {}", fileName);
        }
        return isValid;
    }

    private boolean isTimePeriodValid() {
        try {
            FileNameParts parts = parseFileName();
            return validateFileNameParts(parts);
        } catch (Exception e) {
            log.error("Error parsing file name parts: {}", fileName, e);
            return false;
        }
    }

    private FileNameParts parseFileName() {
        String[] parts = fileName.replace("_", " ").split("\\s");
        return new FileNameParts(
                Long.parseLong(parts[SERIAL_NUMBER_INDEX]),
                Integer.parseInt(parts[YEAR_INDEX]),
                Integer.parseInt(parts[TIME_PERIOD_INDEX]),
                parts[EVENT_NAME_INDEX].toLowerCase(),
                parts[MONTH_NAME_INDEX].toLowerCase()
        );
    }

    private boolean validateFileNameParts(FileNameParts parts) {
        if (!isValidSerialNumber(parts.serialNumber)) {
            log.error("Invalid serial number: {}", parts.serialNumber);
            return false;
        }
        if (!isValidYear(parts.year)) {
            log.error("Invalid year: {}", parts.year);
            return false;
        }
        if (!isValidEventAndMonth(parts.eventName, parts.monthName)) {
            log.error("Invalid event type or month: event={}, month={}", parts.eventName, parts.monthName);
            return false;
        }
        return isValidEventTimePeriod(parts);
    }

    private boolean isValidSerialNumber(long serialNumber) {
        return serialNumber >= MIN_VALID_SERIAL;
    }

    private boolean isValidYear(int year) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return year >= MIN_YEAR && year <= currentYear;
    }

    private boolean isValidEventAndMonth(String eventName, String monthName) {
        Optional<EventType> eventType = EventType.fromString(eventName);
        if (eventType.isEmpty()) {
            return false;
        }
        return isValidMonth(monthName);
    }

    private boolean isValidMonth(String monthName) {
        return VALID_MONTHS_SHORT.contains(monthName) || VALID_MONTHS_FULL.contains(monthName);
    }

    private boolean isValidEventTimePeriod(FileNameParts parts) {
        Optional<EventType> eventType = EventType.fromString(parts.eventName);
        if (eventType.isEmpty()) {
            return false;
        }

        EventType event = eventType.get();

        return switch (event) {
            case DAY -> isValidDayEvent(parts.timePeriod, parts.monthName, parts.year);
            case WEEK -> event.isValidPeriod(parts.timePeriod);
            case MONTH -> isValidMonthEvent(parts.timePeriod, parts.monthName);
            case YEAR -> isValidYearEvent(parts.timePeriod, parts.monthName);
        };
    }

    private boolean isValidDayEvent(int day, String monthName, int year) {
        try {
            String fullMonthName = SHORT_TO_FULL_MONTH.getOrDefault(monthName, monthName);
            Month month = Month.valueOf(fullMonthName.toUpperCase());
            YearMonth yearMonth = YearMonth.of(year, month);
            int daysInMonth = yearMonth.lengthOfMonth();

            if (day >= 1 && day <= daysInMonth) {
                log.info("Valid day event: day={}, month={}, year={}", day, monthName, year);
                return true;
            }
            log.error("Invalid day for month: day={}, max days={}, month={}, year={}", day, daysInMonth, monthName, year);
            return false;
        } catch (IllegalArgumentException e) {
            log.error("Invalid month name: {}", monthName, e);
            return false;
        }
    }

    private boolean isValidMonthEvent(int timePeriod, String monthName) {
        try {
            String fullMonthName = SHORT_TO_FULL_MONTH.getOrDefault(monthName, monthName);
            Month month = Month.valueOf(fullMonthName.toUpperCase());
            boolean isValid = timePeriod == month.getValue();
            if (!isValid) {
                log.error("Invalid month event: timePeriod={}, expectedMonth={}", timePeriod, month.getValue());
            }
            return isValid;
        } catch (IllegalArgumentException e) {
            log.error("Invalid month name: {}", monthName, e);
            return false;
        }
    }

    private boolean isValidYearEvent(int timePeriod, String monthName) {
        try {
            String fullMonthName = SHORT_TO_FULL_MONTH.getOrDefault(monthName, monthName);
            Month month = Month.valueOf(fullMonthName.toUpperCase());
            boolean isValid = timePeriod == 1 && month == Month.DECEMBER;
            if (!isValid) {
                log.error("Invalid year event: timePeriod must be 1 and month must be December. Got: timePeriod={}, month={}", timePeriod, monthName);
            }
            return isValid;
        } catch (IllegalArgumentException e) {
            log.error("Invalid month name: {}", monthName, e);
            return false;
        }
    }

    private FileValidationResponse invalidResponse(ErrorCodes errorCode, String message) {
        log.error(message);
        return new FileValidationResponse(false, errorCode);
    }

    // Inner class to hold parsed file name components
        private record FileNameParts(long serialNumber, int year, int timePeriod, String eventName, String monthName) {
    }
}
