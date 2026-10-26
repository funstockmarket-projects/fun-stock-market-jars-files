package modeling.globalEnums;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class GlobalConstants {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

    LocalTime morningTime = LocalTime.parse("9:15 AM", formatter);
    LocalTime afternoonTime = LocalTime.parse("3:30 PM", formatter);

    LocalDate targetDate = LocalDate.now();

    private LocalDateTime morningDateTime = targetDate.atTime(morningTime);
    private LocalDateTime afternoonDateTime = targetDate.atTime(afternoonTime);
}