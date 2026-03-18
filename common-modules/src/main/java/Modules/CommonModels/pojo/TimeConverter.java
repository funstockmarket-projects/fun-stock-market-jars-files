package Modules.CommonModels.pojo;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter {

    public static String timeConverterToIST(String input){

        input = input.replaceAll("[\\[\\]]", "");

        LocalDateTime localDateTime = LocalDateTime.parse(input);

        ZonedDateTime istTime = localDateTime
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Kolkata"));

        // 🔥 format: date + hour + minute only
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return istTime.format(formatter);
    }
}
