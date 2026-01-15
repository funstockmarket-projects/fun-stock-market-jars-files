package Modules.CommonModels.enums;

import lombok.Getter;

@Getter
public enum FileValidationStatus {

    CLEARED("CLEARED"),
    REJECTED("REJECTED"),
    NOT_CLEARED("NOT_CLEARED");

    private final String status;

    FileValidationStatus(String status) {

        this.status = status;
    }
}
