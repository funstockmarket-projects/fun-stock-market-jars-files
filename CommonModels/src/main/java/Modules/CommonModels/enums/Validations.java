package Modules.CommonModels.enums;

import lombok.Getter;

@Getter
public enum Validations {
    INVALID("INVALID"),
    VALID("VALID"),
    VALIDATED("VALIDATED"),
    AT_TO_UPDATE("AT_TO_UPDATE"),
    GIT_FILE_VALIDATION_FALSE("GIT_FILE_VALIDATION_FALSE"),
    GIT_FILE_VALIDATION_TRUE("GIT_FILE_VALIDATION_TRUE"),
    FILE_VALIDATION_FALSE("FILE_VALIDATION_FALSE"),
    FILE_VALIDATION_TRUE("FILE_VALIDATION_TRUE");

    private final String getValidation;

    Validations(String getValidation) {
        this.getValidation = getValidation;
    }
}
