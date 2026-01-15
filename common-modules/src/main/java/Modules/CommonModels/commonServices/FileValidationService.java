package Modules.CommonModels.commonServices;

import Modules.CommonModels.exceptions.FileErrorContextException;
import com.fsm.domins.clearing.enums.ErrorCodes;
import com.fsm.domins.stockDetails.models.StockFileDetails;
import com.fsm.domins.globalenums.MarketEvents;

import java.util.Set;

public class FileValidationService {

    private static final Set<String> VALID_EVENT_TYPES = Set.of("DAILY", "MONTHLY", "WEEKLY", "YEARLY");

    public void getFileValidationStatus(StockFileDetails stockFileDetails) throws FileErrorContextException {
        require(stockFileDetails != null, ErrorCodes.ERR_3002);

        require(stockFileDetails.getFileUUID() != null && !stockFileDetails.getFileUUID().isEmpty(), ErrorCodes.ERR_3002);
        require(stockFileDetails.getFileName() != null && !stockFileDetails.getFileName().isEmpty(), ErrorCodes.ERR_3001);
        require(stockFileDetails.getFolderName() != null && !stockFileDetails.getFolderName().isEmpty(), ErrorCodes.ERR_3003);
        require(stockFileDetails.getFileType() != null && !stockFileDetails.getFileType().isEmpty(), ErrorCodes.ERR_3004);

        Long fileSize = stockFileDetails.getFileSize();
        require(fileSize != null && fileSize > 0, ErrorCodes.ERR_3005);

        long numberOfRecords = stockFileDetails.getNumberOfRecords();
        require(numberOfRecords > 0, ErrorCodes.ERR_3006);

        require(stockFileDetails.getUri() != null && !stockFileDetails.getUri().isEmpty(), ErrorCodes.ERR_3007);
        require(stockFileDetails.getFileData() != null && !stockFileDetails.getFileData().isEmpty(), ErrorCodes.ERR_3008);
        require(stockFileDetails.getFileUploadDate() != null, ErrorCodes.ERR_3009);
        require(stockFileDetails.getFileModifiedDate() != null, ErrorCodes.ERR_3010);

        MarketEvents marketEvents = stockFileDetails.getEventName();
        require(marketEvents != null, ErrorCodes.ERR_3012);

        String eventName = marketEvents.getEventName();
        require(eventName != null && VALID_EVENT_TYPES.contains(eventName.toUpperCase()), ErrorCodes.ERR_3012);
    }

    // Helper to reduce repetition: throws the provided error code if the condition is false
    private void require(boolean condition, ErrorCodes errorCode) throws FileErrorContextException {
        if (!condition) {
            throw new FileErrorContextException(errorCode);
        }
    }
}
