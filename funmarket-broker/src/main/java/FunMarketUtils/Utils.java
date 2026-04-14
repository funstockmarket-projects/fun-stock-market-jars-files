package FunMarketUtils;

import com.fsm.domainsMapping.constantsBO.AnswerBO;
import com.fsm.domainsMapping.constantsBO.DaysBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    private static final String TIMEZONE_ASIA_KOLKATA = "Asia/Kolkata";
    public static final String UNKNOWN = "UNKNOWN";
    public static final String NOT_PROCESSED= "NOT_PROCESSED";

    public static String getStringValue(Map<String, Object> metadata, String key, String defaultValue) {
        Object value = metadata.getOrDefault(key, defaultValue);
        return value instanceof String ? (String) value : defaultValue;
    }

    public static Long getLongValue(Map<String, Object> metadata, String key, Long defaultValue) {
        Object value = metadata.get(key);
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        return defaultValue;
    }

    public static AnswerBO isWorkingDay(DayOfWeek dayOfWeek) {
        return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
                ? AnswerBO.N
                : AnswerBO.Y;
    }

    public static DaysBO convertDayOfWeekToDaysEnum(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> DaysBO.MONDAY;
            case TUESDAY -> DaysBO.TUESDAY;
            case WEDNESDAY -> DaysBO.WEDNESDAY;
            case THURSDAY -> DaysBO.THURSDAY;
            case FRIDAY -> DaysBO.FRIDAY;
            case SATURDAY -> DaysBO.SATURDAY;
            case SUNDAY -> DaysBO.SUNDAY;
        };
    }

    public static LocalDateTime parseUploadDateTime(Map<String, Object> gitMetadata, String fieldName) {
        try {
            String uploadTimeStr = (String) gitMetadata.getOrDefault(fieldName, LocalDateTime.now().toString());

            // Handle format with space and nanoseconds: "2026-04-13T14:05:09 990107400"
            if (uploadTimeStr.contains(" ")) {
                uploadTimeStr = uploadTimeStr.split(" ")[0];
            }

            // Try to parse as LocalDateTime first (handles ISO format without timezone)
            try {
                return LocalDateTime.parse(uploadTimeStr);
            } catch (Exception localDateTimeException) {
                // If LocalDateTime parsing fails, try ZonedDateTime
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(uploadTimeStr);
                return zonedDateTime.toLocalDateTime();
            }
        } catch (Exception e) {
            log.warn("Failed to parse upload time for field: {}, using current time. Value was: {}", fieldName, gitMetadata.get(fieldName), e);
            return LocalDateTime.now();
        }
    }

    public static LocalDateTime currentTime() {
        return LocalDateTime.now(ZoneId.of(TIMEZONE_ASIA_KOLKATA));
    }
}
