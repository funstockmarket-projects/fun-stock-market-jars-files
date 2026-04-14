package funMarketClearing.clearingEngine.validation;

import com.fsm.domainsMapping.businessObject.marketHolidayCalender.HolidayCalendarBO;
import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.ErrorCodesBO;
import funMarketClearing.Exception.FileErrorContextException;
import funMarketClearing.clearingEngine.validation.fileOperations.FileNameToDateConversion;
import funMarketExceptions.FunMarketException;
import funMarketMarketHolidayCalender.operations.HolidayCalendarServiceEng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Component
public class IsHolidayFileOnDailyFolder extends FileValidationAbstractService {

    private static final Logger log = LoggerFactory.getLogger(IsHolidayFileOnDailyFolder.class);
    private static final String DAILY_FOLDER_NAME = "dailyPerformance";



    @Autowired
    @Qualifier(value = "holidayCalendarServiceEng")
    private HolidayCalendarServiceEng holidayCalendarServiceEng;

    @Override
    public void process(FileMetadataBO stockFileDetailsBO) {
        if (stockFileDetailsBO == null) {
            throw new FunMarketException("Input file details is null");
        }

        String fileName = stockFileDetailsBO.getFileName();
        String folderName = stockFileDetailsBO.getFolderName();

        if (isStringInvalid(fileName) || isStringInvalid(folderName)) {
            log.error("Missing metadata. fileName: [ {} ], folderName: [ {} ]", fileName, folderName);
            throw new FileErrorContextException("Input values cannot be null/empty", ErrorCodesBO.ERR_1001);
        }

        if (DAILY_FOLDER_NAME.equals(folderName)) {
            validateHolidayEvent(fileName);
        }
    }

    private void validateHolidayEvent(String fileName) {

        LocalDate parsedDate = FileNameToDateConversion.getDate(fileName);
        String formattedDate = parsedDate.toString();

        if (holidayCalendarServiceEng == null) {
            log.error("HolidayCalendarServiceEng is not initialized");
            throw new FileErrorContextException(ErrorCodesBO.ERR_0000);
        }

        List<HolidayCalendarBO> holidays = holidayCalendarServiceEng.findByHolidayAt(formattedDate);
        if (!holidays.isEmpty()) {
            log.warn("File rejected: Holiday detected. File: [ {} ], Date: [ {} ]", fileName, formattedDate);
            throw new FileErrorContextException(ErrorCodesBO.ERR_301);
        }

        // 3. Weekend Check
        DayOfWeek dayOfWeek = parsedDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            log.warn("File rejected: Weekend detected. File: [ {} ], Day: [ {} ]", fileName, dayOfWeek);
            throw new FileErrorContextException(ErrorCodesBO.ERR_302);
        }
    }



    private boolean isStringInvalid(String str) {
        return str == null || str.isBlank();
    }
}