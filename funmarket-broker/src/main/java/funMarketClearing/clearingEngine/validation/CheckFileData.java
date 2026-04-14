package funMarketClearing.clearingEngine.validation;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.ErrorCodesBO;
import funMarketClearing.Exception.FileErrorContextException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CheckFileData extends FileValidationAbstractService {

    private final Logger log = LoggerFactory.getLogger(CheckFileData.class);

    @Override
    public void process(FileMetadataBO stockFileDetailsBO) {
        log.info("Starting file validation process for file: {}", stockFileDetailsBO != null ? stockFileDetailsBO.getFileName() : "NULL_OBJECT");

        try {
            if (stockFileDetailsBO == null) {
                log.error("Validation failed: FileMetadataBO is null");
                throw new FileErrorContextException(ErrorCodesBO.ERR_1000);
            }
            validateString(stockFileDetailsBO.getFileUUID(), ErrorCodesBO.ERR_3002, "File UUID");
            validateString(stockFileDetailsBO.getFileName(), ErrorCodesBO.ERR_1001, "File Name");
            validateString(stockFileDetailsBO.getFolderName(), ErrorCodesBO.ERR_3003, "Folder Name");
            validateString(stockFileDetailsBO.getFileType(), ErrorCodesBO.ERR_3004, "File Type");
            validateString(stockFileDetailsBO.getUri(), ErrorCodesBO.ERR_3007, "URI");

            if (stockFileDetailsBO.getFileSize() == null || stockFileDetailsBO.getFileSize() <= 0) {
                log.error("Validation failed: Invalid file size [{}] for file: {}", stockFileDetailsBO.getFileSize(), stockFileDetailsBO.getFileName());
                throw new FileErrorContextException(ErrorCodesBO.ERR_3005);
            }

            if (stockFileDetailsBO.getNumberOfRecords() <= 0) {
                log.error("Validation failed: Invalid record count [{}] for file: {}", stockFileDetailsBO.getNumberOfRecords(), stockFileDetailsBO.getFileName());
                throw new FileErrorContextException(ErrorCodesBO.ERR_3006);
            }

            if (stockFileDetailsBO.getEventNameBO() == null) {
                log.error("Validation failed: MarketEventsBO is missing for file: {}", stockFileDetailsBO.getFileName());
                throw new FileErrorContextException(ErrorCodesBO.ERR_3012);
            }

            if (stockFileDetailsBO.getFileUploadDate() == null) {
                log.error("Validation failed: File upload date is missing");
                throw new FileErrorContextException(ErrorCodesBO.ERR_3009);
            }
            if (stockFileDetailsBO.getFileModifiedDate() == null) {
                log.error("Validation failed: File modified date is missing");
                throw new FileErrorContextException(ErrorCodesBO.ERR_3010);
            }

            List<Map<String, Object>> data = stockFileDetailsBO.getFileData();
            if (data == null || data.isEmpty()) {
                log.error("Validation failed: File data is null or empty for file: {}", stockFileDetailsBO.getFileName());
                throw new FileErrorContextException(ErrorCodesBO.ERR_3008);
            }

            log.info("Validation successful for File UUID: {} | Records: {}", stockFileDetailsBO.getFileUUID(), stockFileDetailsBO.getNumberOfRecords());

        } catch (FileErrorContextException e) {
            throw e;
        } catch (Exception e) {
            // Catch unexpected runtime exceptions (NullPointer, etc.)
            log.error("Unexpected error during file processing: ", e);
            throw new FileErrorContextException(ErrorCodesBO.ERR_0000);
        }
    }

    /**
     * Utility method to validate strings and log errors consistently.
     */
    private void validateString(String value, ErrorCodesBO errorCode, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            log.error("Validation failed: {} is null or empty", fieldName);
            throw new FileErrorContextException(errorCode);
        }
    }
}