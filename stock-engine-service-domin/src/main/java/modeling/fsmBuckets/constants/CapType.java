package modeling.fsmBuckets.constants;

import lombok.Getter;

@Getter
public enum CapType {
    SMALL("small"),
    LARGE("large"),
    MID("mid");

    private final String capType;

    CapType(String capType) {
        this.capType = capType;
    }
}
