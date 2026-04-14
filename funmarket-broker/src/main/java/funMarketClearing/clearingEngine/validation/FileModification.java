package funMarketClearing.clearingEngine.validation;

import com.fsm.domins.stockDetails.operations.FunMarketStockFileDetailsRetrievalMethods;
import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.ErrorCodesBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import funMarketClearing.Exception.FileErrorContextException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FileModification extends FileValidationAbstractService {

    private final Logger log = LoggerFactory.getLogger(FileModification.class);

    @Autowired
    @Qualifier(value = "funMarketStockFileDetailsRetrievals")
    private FunMarketStockFileDetailsRetrievalMethods funMarketClearingOperations;

    @Override
    public void process(FileMetadataBO stockFileDetailsBO) {

        if (stockFileDetailsBO == null) {
            log.error("Validation failed: FileMetadataBO is null");
            throw new FileErrorContextException(ErrorCodesBO.ERR_1000);
        }

        if (RecordStatusBO.MODIFIED.equals(stockFileDetailsBO.getRecordStatusBO())) {
            log.info("Modification detected for File UUID: [ {} ]. Starting consistency check.", stockFileDetailsBO.getFileUUID());

            FileMetadataBO existingFileMetadataBO = retrieveExistingData(stockFileDetailsBO.getFileUUID());

            if (existingFileMetadataBO != null) {
                performModificationValidation(stockFileDetailsBO, existingFileMetadataBO);
            } else {
                log.warn("Modification requested but no existing record found for UUID: [ {} ], FileName [ {} ]", stockFileDetailsBO.getFileUUID(), stockFileDetailsBO.getFileName());
                throw new FileErrorContextException(ErrorCodesBO.ERR_100);
            }
        }

        // ... Continue with the rest of your pattern and data validation ...
    }

    /**
     * Compares incoming data with existing data for consistency during modification.
     */
    private void performModificationValidation(FileMetadataBO incoming, FileMetadataBO existing) {

        if (isEqual(incoming.getFileUUID(), existing.getFileUUID())) {
            log.error("Modification Error: File UUID mismatch. Incoming: [ {} ], Existing: [ {} ]", incoming.getFileUUID(), existing.getFileUUID());
            throw new FileErrorContextException(ErrorCodesBO.ERR_101);
        }

        if (isEqual(incoming.getFileName(), existing.getFileName())) {
            log.error("Modification Error: File Name mismatch for UUID:[ {} ]", incoming.getFileUUID());
            throw new FileErrorContextException(ErrorCodesBO.ERR_102);
        }

        if (!isDateEqual(incoming.getFileUploadDate(), existing.getFileUploadDate())) {
            log.error("Modification Error: Upload Date mismatch for UUID: {}", incoming.getFileUUID());
            throw new FileErrorContextException(ErrorCodesBO.ERR_103);
        }

        log.info("Consistency check passed for modified record: {}", incoming.getFileUUID());
    }

    /**
     * Helper to safely compare strings/objects
     */
    private boolean isEqual(Object incoming, Object existing) {
        if (incoming == null && existing == null) return false;
        if (incoming == null || existing == null) return true;
        return !incoming.equals(existing);
    }

    /**
     * Helper to compare LocalDateTime (ignoring nanoseconds if necessary)
     */
    private boolean isDateEqual(LocalDateTime incoming, LocalDateTime existing) {
        if (incoming == null && existing == null) return true;
        if (incoming == null || existing == null) return false;
        return incoming.equals(existing);
    }

    // Mock method a() - Replace with your actual retrieval logic
    private FileMetadataBO retrieveExistingData(String uuid) {
        return funMarketClearingOperations.findByFileUUID(uuid);
    }
}