package modeling.fsmBuckets.constants;

import lombok.Getter;

@Getter
public enum TreadingStatus {

    ACTIVE("active"),
    INACTIVE("inActive");

    private final String treadingStatus;

    TreadingStatus(String treadingStatus) {
        this.treadingStatus = treadingStatus;
    }
}
