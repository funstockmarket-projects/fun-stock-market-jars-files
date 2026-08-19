package modeling.fsmBuckets.constants;

import lombok.Getter;

@Getter
public enum RecordStatus {
    ADDED("added"),
    MODIFIED("modified");

    private final String recordStatus;

    RecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }
}
