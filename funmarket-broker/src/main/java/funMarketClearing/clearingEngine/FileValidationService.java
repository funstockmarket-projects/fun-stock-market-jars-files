package funMarketClearing.clearingEngine;

import com.fsm.domins.clearing.models.FileClearing;
import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import funMarketClearing.Exception.FileErrorContextException;
import funMarketClearing.Operations.FunMarketClearingOperations;
import funMarketClearing.clearingEngine.validation.*;
import funMarketExceptions.FunMarketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static funMarketClearing.constants.FileValidationStatus.*;

@Component(value = "fileValidationService")
public class FileValidationService {

    private final Logger log = LoggerFactory.getLogger(FileValidationService.class);

    private final FunMarketClearingOperations funMarketClearingOperations;
    private final CheckFileData checkFileData;
    private final CheckFileNamePattern checkFileNamePattern;
    private final FileTimePeriod fileTimePeriod;
    private final IsHolidayFileOnDailyFolder isHolidayFileOnDailyFolder;
    private final FileModification fileModification;

    @Autowired
    public FileValidationService(FunMarketClearingOperations funMarketClearingOperations,
                                 CheckFileData checkFileData, CheckFileNamePattern checkFileNamePattern, FileTimePeriod fileTimePeriod,
                                 IsHolidayFileOnDailyFolder isHolidayFileOnDailyFolder, FileModification fileModification) {
        this.funMarketClearingOperations = funMarketClearingOperations;
        this.checkFileData = checkFileData;
        this.checkFileNamePattern = checkFileNamePattern;
        this.fileTimePeriod = fileTimePeriod;
        this.isHolidayFileOnDailyFolder = isHolidayFileOnDailyFolder;
        this.fileModification = fileModification;
    }

    private static final String TIMEZONE_ASIA_KOLKATA = "Asia/Kolkata";

    private void validationEngine(FileMetadataBO stockFileDetailsBO) {

        final LocalDateTime currentTime = currentTime();

        if (stockFileDetailsBO == null || stockFileDetailsBO.getFileName() == null) {
            log.error("Input data cannot be null. Date [ {} ]", currentTime());
            throw new FunMarketException("Input data cannot be null. Date [ " + currentTime() + " ]");
        }
        final String fileName = stockFileDetailsBO.getFileName();

        log.info("Started file validation for the file, FileName [ {} ], Date: [ {} ]", fileName, currentTime);

        checkFileData.process(stockFileDetailsBO);
        checkFileNamePattern.process(stockFileDetailsBO);
        fileTimePeriod.process(stockFileDetailsBO);
        isHolidayFileOnDailyFolder.process(stockFileDetailsBO);
        fileModification.process(stockFileDetailsBO);

        log.info("Validation completed for the FileName [ {} ]", fileName);

    }

    private static LocalDateTime currentTime() {
        return LocalDateTime.now(ZoneId.of(TIMEZONE_ASIA_KOLKATA));
    }

    private void processFileValidateEngine(FileMetadataBO stockFileDetailsBO) {
        this.validationEngine(stockFileDetailsBO);
    }

    public FileClearingBO initiatingFileClearingProcess(FileMetadataBO stockFileDetailsBO) {
        log.info("initiating clearing process for file name: [ {} ], Date: [ {} }", stockFileDetailsBO.getFileName(), currentTime());
        String errorCode = "";
        String errorMessage = "";
        String validationStatus = "";
        String fileUuid = stockFileDetailsBO.getFileUUID();
        FileClearingBO fileClearingBO =null;
        try {
            this.processFileValidateEngine(stockFileDetailsBO);
            errorMessage = "File cleared successfully";
            errorCode = "2000";
            validationStatus = CLEARED.name();
        } catch (FileErrorContextException e) {
            log.error("Error initiating clearing process for file name: {}, ErrorCode: {}, reason: {}", stockFileDetailsBO.getFileName(), e.getCode(), e.getMessage());
            errorMessage = e.getMessage();
            errorCode = e.getCode();
            validationStatus = REJECTED.name();
        } catch (Exception e) {
            log.error("Unexpected error during clearing process for file name: {}, reason: {}", stockFileDetailsBO.getFileName(), e.getMessage());
            errorMessage = "Unexpected error: " + e.getMessage();
            errorCode = "500";
            validationStatus = NOT_CLEARED.name();
        }finally {
            log.info("Clearing process completed for fileUuid: {},validation status: {}, error code: {}, Error message: {}", fileUuid, validationStatus, errorCode, errorMessage);
            fileClearingBO = funMarketClearingOperations.saveClearing(fileUuid, stockFileDetailsBO.getFileName(), validationStatus, errorCode, errorMessage, FileClearing.class.getSimpleName(), RecordStatusBO.ADDED);
        }
        return fileClearingBO;
    }
}
