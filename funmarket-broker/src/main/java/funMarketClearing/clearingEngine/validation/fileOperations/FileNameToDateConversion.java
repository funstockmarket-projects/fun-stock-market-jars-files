package funMarketClearing.clearingEngine.validation.fileOperations;

import com.fsm.domainsMapping.constantsBO.ErrorCodesBO;
import funMarketClearing.Exception.FileErrorContextException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class FileNameToDateConversion {

    private static final Logger log = LoggerFactory.getLogger(FileNameToDateConversion.class);

    private static final DateTimeFormatter DATE_PARSER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("d-")
            .appendPattern("[MMMM][MMM]")
            .appendPattern("-")
            .appendPattern("[yyyy][yy]")
            .toFormatter(Locale.ENGLISH);

    public static LocalDate getDate(String fileName){

        String[] parts = getFileNamePlaceHolders(fileName);

        String year = parts[1];
        String dayStr = parts[2];
        String month = parts[3];
        return buildDate(dayStr, year, month, fileName);
    }

    public static String[] getFileNamePlaceHolders(String fileName){

        String[] parts = fileName.split(" ");
        if (parts.length != 4) {
            throw new FileErrorContextException("Invalid file name format: " + fileName, ErrorCodesBO.ERR_1001);
        }
        if (!parts[2].contains("_day")) {
            throw new FileErrorContextException("Invalid file in day folder", ErrorCodesBO.ERR_401);
        }
        String dayStr = parts[2].replace("_day", "");
        parts[2] = dayStr;

        return parts;
    }

    private static  LocalDate buildDate(String day, String year, String month, String fileName) {
        String dateString = String.format("%s-%s-%s", day, month, year);
        try {
            return LocalDate.parse(dateString, DATE_PARSER);
        } catch (DateTimeParseException e) {
            log.error("Date parsing failed for string: [ {} ] in file: [ {} ]", dateString, fileName);
            throw new FileErrorContextException("Invalid date in filename", ErrorCodesBO.ERR_1001);
        }
    }
}
