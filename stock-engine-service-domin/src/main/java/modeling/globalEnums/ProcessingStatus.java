package modeling.globalEnums;

import lombok.Getter;

@Getter
public enum ProcessingStatus {
    INPROGRESS("INPROGRESS"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String processingStatus;

    ProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }
}
