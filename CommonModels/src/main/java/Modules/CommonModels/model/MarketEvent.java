package Modules.CommonModels.model;

import Modules.CommonModels.enums.FileStatus;
import Modules.CommonModels.enums.MarketEvents;
import Modules.CommonModels.enums.Validations;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class MarketEvent {
    static {
        log.info("StockFiles Entity Initialized");
    }

    private Long marketEventId;

    private MarketEvents marketEventName;

    private LocalDateTime localDateTime = LocalDateTime.now();

    private List<MarketFileDetails> marketFileDetails;

    public boolean isFileValid() {
        if (this.marketEventName == null || this.marketEventName.getEventName() == null) {
            log.warn("MarketEvents is null for MarketFiles: {}", this);
            return true;
        }
        if (this.marketFileDetails == null || this.marketFileDetails.isEmpty()) {
            log.warn("MarketFileDetails list is empty for MarketFiles: {}", this);
            return true;
        }
        for (MarketFileDetails fileDetail : this.marketFileDetails) {
            if (fileDetail.isFileValid()) {
                fileDetail.setFileValidationStatus(Validations.INVALID);
                fileDetail.setFileStatus(FileStatus.INCOMPLETE);
                log.warn("Invalid MarketFileDetails found in MarketFiles: {}", this);
            } else {
                fileDetail.setFileValidationStatus(Validations.VALID);
                fileDetail.setFileStatus(FileStatus.UPDATED);
            }
        }
        return false;
    }
}