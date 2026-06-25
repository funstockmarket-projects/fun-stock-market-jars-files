package modeling.globalEnums;

import lombok.Getter;

@Getter
public enum RecordStatus {
    ADDED("ADDED"),
    MODIFIED("MODIFIED");

    private final String value;


    RecordStatus(String value) {
        this.value = value;
    }
}
