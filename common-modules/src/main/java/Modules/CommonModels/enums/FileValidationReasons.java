package Modules.CommonModels.enums;

public enum FileValidationReasons {

    //INVALID REASONS
    FILENAME_INCORRECT("Filename is incorrect"),
    FILETYPE_INCORRECT("File type is unsupported"),
    DOWNLOAD_URL_INCORRECT("Download URL is incorrect"),
    FOLDER_NAME_INCORRECT("Folder name is incorrect"),
    FILE_SIZE("File size exceeds the limit"),
    NUMBER_OF_RECORDS_INCORRECT("Number of records in the file is incorrect"),
    MARKET_EVENT_MISMATCH("Market event does not match the file content"),
    FILE_CREATION_DATE_INVALID("File creation Date is incorrect"),
    FILE_MODIFICATION_DATE_INVALID("File Modification Date is incorrect"),
    FILE_DATA_INCORRECT("File data is corrupted"),

    //VALIDATION STEPS
    GIT_FILE_VALIDATION_FAILED("Git file validation failed"),
    INVALID_AT_GIT_READER_APPLICATION("Invalid file at Git Reader Application"),
    FINAL_VALIDATION_FAILED("Final validation failed"),

    //VALID REASONS
    GIT_FILE_VALIDATION_SUCCESSFUL("Git file validation successful"),
    VALID_AT_GIT_READER_APPLICATION("Valid file at Git Reader Application"),
    FINAL_VALIDATION_SUCCESSFUL("Final validation successful");


    private final String reason;

    FileValidationReasons(String reason) {
        this.reason = reason;
    }
}
