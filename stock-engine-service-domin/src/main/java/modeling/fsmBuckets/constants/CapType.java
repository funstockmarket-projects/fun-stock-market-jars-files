package modeling.fsmBuckets.constants;

import lombok.Getter;

@Getter
public enum CapType {
    SMALL("SMALL"),
    LARGE("LARGE"),
    MID("MID");

    private final String capType;

    CapType(String capType) {
        this.capType = capType;
    }
}
