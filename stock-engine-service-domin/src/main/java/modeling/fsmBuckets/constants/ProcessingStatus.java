package modeling.fsmBuckets.constants;

import lombok.Getter;

@Getter
public enum ProcessingStatus {
    INPOGRESS("inProgress"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String processingStatus;

    ProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }
}
