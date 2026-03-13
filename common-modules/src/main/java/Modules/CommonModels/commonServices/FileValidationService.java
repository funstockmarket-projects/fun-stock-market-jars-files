package Modules.CommonModels.commonServices;

import Modules.CommonModels.exceptions.FileErrorContextException;
import Modules.fileValidation.FileNameValidation;
import Modules.fileValidation.FileValidationResponse;
import com.fsm.domins.clearing.enums.ErrorCodes;
import com.fsm.domins.stockDetails.models.StockFileDetails;
import com.fsm.domins.globalenums.MarketEvents;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class FileValidationService {

    private static final Set<String> VALID_EVENT_TYPES = Set.of("DAILY", "MONTHLY", "WEEKLY", "YEARLY");
    private static final long MIN_RECORDS = 1L;

    public void getFileValidationStatus(StockFileDetails stockFileDetails) throws FileErrorContextException {
        log.debug("Starting validation for StockFileDetails");

        require(stockFileDetails != null, ErrorCodes.ERR_3002, "StockFileDetails cannot be null");

        assert stockFileDetails != null;
        validateFileName(stockFileDetails.getFileName());
        validateFileMetadata(stockFileDetails);
        validateFileProperties(stockFileDetails);
        validateFileContent(stockFileDetails);
        validateEventDetails(stockFileDetails);

        log.info("All validations passed successfully");
    }

    private void validateFileName(String fileName) throws FileErrorContextException {
        FileValidationResponse response = new FileNameValidation(fileName).processFileName();

        if (!response.fileValidationResult) {
            log.error("File name validation failed: {}", fileName);
            require(false, response.getErrorCodes(), "Invalid file name format: " + fileName);
        }
    }

    private void validateFileMetadata(StockFileDetails details) throws FileErrorContextException {
        require(isNotBlank(details.getFileUUID()), ErrorCodes.ERR_3002, "File UUID cannot be empty");
        require(isNotBlank(details.getFolderName()), ErrorCodes.ERR_3003, "Folder name cannot be empty");
        require(isNotBlank(details.getFileType()), ErrorCodes.ERR_3004, "File type cannot be empty");
        log.debug("File metadata validation passed");
    }

    private void validateFileProperties(StockFileDetails details) throws FileErrorContextException {
        require(details.getFileSize() != null, ErrorCodes.ERR_3005, "File size cannot be null");
        require(details.getFileSize() > 0, ErrorCodes.ERR_3005, "File size must be greater than 0");

        require(details.getNumberOfRecords() >= MIN_RECORDS, ErrorCodes.ERR_3006,
                "Number of records must be at least " + MIN_RECORDS);

        log.debug("File properties validation passed - Size: {}, Records: {}",
                details.getFileSize(), details.getNumberOfRecords());
    }

    private void validateFileContent(StockFileDetails details) throws FileErrorContextException {
        require(isNotBlank(details.getUri()), ErrorCodes.ERR_3007, "URI cannot be empty");
        require(details.getFileData() != null && !details.getFileData().isEmpty(),
                ErrorCodes.ERR_3008, "File data cannot be empty");

        log.debug("File content validation passed");
    }

    private void validateEventDetails(StockFileDetails details) throws FileErrorContextException {
        require(details.getFileUploadDate() != null, ErrorCodes.ERR_3009, "Upload date cannot be null");
        require(details.getFileModifiedDate() != null, ErrorCodes.ERR_3010, "Modified date cannot be null");

        MarketEvents eventName = details.getEventName();
        require(eventName != null, ErrorCodes.ERR_3012, "Event name cannot be null");

        String eventValue = eventName.getEventName();
        require(eventValue != null && VALID_EVENT_TYPES.contains(eventValue.toUpperCase()),
                ErrorCodes.ERR_3012, "Invalid event type: " + eventValue);

        log.debug("Event details validation passed - Event: {}", eventValue);
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.isEmpty();
    }

    private void require(boolean condition, ErrorCodes errorCode, String message) throws FileErrorContextException {
        if (!condition) {
            log.error("Validation failed: {} [{}]", message, errorCode.toString());
            throw new FileErrorContextException(errorCode);
        }
    }
}
