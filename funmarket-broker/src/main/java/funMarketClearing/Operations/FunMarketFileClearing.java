package funMarketClearing.Operations;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domins.clearing.operations.FileClearingSaveOperations;
import funMarketExceptions.FunMarketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static FunMarketUtils.Utils.currentTime;

@Service(value = "funMarketFileClearing")
public class FunMarketFileClearing implements FunMarketClearingOperations {

    private final Logger log = LoggerFactory.getLogger(FunMarketFileClearing.class);

    private final FileClearingSaveOperations fileClearingRetrievalMethods;

    public FunMarketFileClearing(@Qualifier(value = "funMarketSaveFileClearing") FileClearingSaveOperations fileClearingRetrievalMethods) {
        this.fileClearingRetrievalMethods = fileClearingRetrievalMethods;
    }

    @Override
    public FileClearingBO saveClearing(String fileUuid, String fileName, String validationStatus, String errorCode, String errorMessage, String placeOFModification, RecordStatusBO recordStatus) {
        try {
            if (fileUuid == null || fileName == null || validationStatus == null || errorCode == null || errorMessage == null || placeOFModification == null || recordStatus == null) {
                log.warn("All parameters must be provided and non-null to save clearing information. Received parameters - fileUuid: {}, fileName: {}, validationStatus: {}, errorCode: {}, errorMessage: {}, placeOFModification: {}, recordStatus: {}", fileUuid, fileName, validationStatus, errorCode, errorMessage, placeOFModification, recordStatus);
                throw new IllegalArgumentException("All parameters must be provided and non-null to save clearing information.");
            }

            FileClearingBO fileClearingBO = buildFileClearingObject(fileUuid, fileName, validationStatus, errorCode, errorMessage, placeOFModification, recordStatus);
            fileClearingBO = fileClearingRetrievalMethods.save(fileClearingBO);
            log.info("Successfully created FileClearingBO for fileUuid: {}", fileUuid);
            return fileClearingBO;
        } catch (Exception e) {
            log.error("Error creating FileClearingBO for fileUuid: {}", fileUuid, e);
            throw new FunMarketException("Failed to create FileClearingBO Message:- " + e);
        }
    }

    public FileClearingBO buildFileClearingObject(String fileUuid, String fileName, String validationStatus, String errorCode, String errorMessage, String placeOFModification, RecordStatusBO recordStatus) {

        log.info("Creating new FileClearing object for fileUuid: {}", fileUuid);
        String uuid = UUID.randomUUID().toString();
        return FileClearingBO.builder()
                .fileClearingUuid(uuid)
                .fileUuid(fileUuid)
                .fileName(fileName)
                .fileValidationStatus(validationStatus)
                .clearingCode(errorCode)
                .clearingMessage(errorMessage)
                .clearingDate(currentTime())
                .modifiedDate(currentTime())
                .placeOFModification(placeOFModification)
                .clearingRecordStatus(recordStatus)
                .build();
    }
}

