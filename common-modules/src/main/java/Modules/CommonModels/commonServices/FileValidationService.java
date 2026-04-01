package Modules.CommonModels.commonServices;

import Modules.CommonModels.exceptions.FileErrorContextException;
import Modules.fileValidation.FileNameValidation;
import Modules.fileValidation.FileValidationResponse;
import com.fsm.dominsMapping.businessObject.stockDetailsBO.StockFileDetailsBO;
import com.fsm.dominsMapping.constantsBO.ErrorCodesBO;
import com.fsm.dominsMapping.constantsBO.MarketEventsBO;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class FileValidationService {

    private static final Set<String> VALID_EVENT_TYPES = Set.of("DAILY", "MONTHLY", "WEEKLY", "YEARLY");
    private static final long MIN_RECORDS = 1L;

    public void getFileValidationStatus(StockFileDetailsBO StockFileDetailsBO) throws FileErrorContextException {
        log.debug("Starting validation for StockFileDetailsBO");

        require(StockFileDetailsBO != null, ErrorCodesBO.ERR_3002, "StockFileDetailsBO cannot be null");

        assert StockFileDetailsBO != null;
        validateFileName(StockFileDetailsBO.getFileName());
        validateFileMetadata(StockFileDetailsBO);
        validateFileProperties(StockFileDetailsBO);
        validateFileContent(StockFileDetailsBO);
        validateEventDetails(StockFileDetailsBO);

        log.info("All validations passed successfully");
    }

    private void validateFileName(String fileName) throws FileErrorContextException {
        FileValidationResponse response = new FileNameValidation(fileName).processFileName();

        if (!response.fileValidationResult) {
            log.error("File name validation failed: {}", fileName);
            require(false, response.getErrorCodes(), "Invalid file name format: " + fileName);
        }
    }

    private void validateFileMetadata(StockFileDetailsBO details) throws FileErrorContextException {
        require(isNotBlank(details.getFileUUID()), ErrorCodesBO.ERR_3002, "File UUID cannot be empty");
        require(isNotBlank(details.getFolderName()), ErrorCodesBO.ERR_3003, "Folder name cannot be empty");
        require(isNotBlank(details.getFileType()), ErrorCodesBO.ERR_3004, "File type cannot be empty");
        log.debug("File metadata validation passed");
    }

    private void validateFileProperties(StockFileDetailsBO details) throws FileErrorContextException {
        require(details.getFileSize() != null, ErrorCodesBO.ERR_3005, "File size cannot be null");
        require(details.getFileSize() > 0, ErrorCodesBO.ERR_3005, "File size must be greater than 0");

        require(details.getNumberOfRecords() >= MIN_RECORDS, ErrorCodesBO.ERR_3006,
                "Number of records must be at least " + MIN_RECORDS);

        log.debug("File properties validation passed - Size: {}, Records: {}",
                details.getFileSize(), details.getNumberOfRecords());
    }

    private void validateFileContent(StockFileDetailsBO details) throws FileErrorContextException {
        require(isNotBlank(details.getUri()), ErrorCodesBO.ERR_3007, "URI cannot be empty");
        require(details.getFileData() != null && !details.getFileData().isEmpty(),
                ErrorCodesBO.ERR_3008, "File data cannot be empty");

        log.debug("File content validation passed");
    }

    private void validateEventDetails(StockFileDetailsBO details) throws FileErrorContextException {
        require(details.getFileUploadDate() != null, ErrorCodesBO.ERR_3009, "Upload date cannot be null");
        require(details.getFileModifiedDate() != null, ErrorCodesBO.ERR_3010, "Modified date cannot be null");

        MarketEventsBO eventName = details.getEventNameBO();
        require(eventName != null, ErrorCodesBO.ERR_3012, "Event name cannot be null");

        String eventValue = eventName.getEventName();
        require(eventValue != null && VALID_EVENT_TYPES.contains(eventValue.toUpperCase()),
                ErrorCodesBO.ERR_3012, "Invalid event type: " + eventValue);

        log.debug("Event details validation passed - Event: {}", eventValue);
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isEmpty();
    }

    private void require(boolean condition, ErrorCodesBO errorCode, String message) throws FileErrorContextException {
        if (!condition) {
            log.error("Validation failed: {} [{}]", message, errorCode.toString());
            throw new FileErrorContextException(errorCode);
        }
    }
}
