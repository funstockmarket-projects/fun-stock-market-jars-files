package Modules.CommonModels.model.marketStockData;

import Modules.CommonModels.enums.FileValidationReasons;
import Modules.CommonModels.enums.MarketEvents;
import Modules.CommonModels.enums.Validations;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockFileDetails {


    private String fileId;
    private String fileName;
    private List<Map<String, Object>> fileData;
    private String folderName;
    private String fileType;
    private Long fileSize;
    private long numberOfRecords;
    private String uri;
    private FileDateValidationStatus fileDataValidation;
    private MarketEvents marketEvents;
    private LocalDateTime fileCreatedDate;
    private LocalDateTime fileModifiedDate = LocalDateTime.now();

    public void isValid() {
        this.fileModifiedDate = LocalDateTime.now();
        List<FileValidationReasons> reasons = this.fileDataValidation != null ?
                this.fileDataValidation.getReason()
                : new ArrayList<>();

        if (this.fileName == null || this.fileName.isEmpty()) {
            reasons.add(FileValidationReasons.FILENAME_INCORRECT);
        }
        if (this.fileType == null || this.fileType.isEmpty()) {
            reasons.add(FileValidationReasons.FILETYPE_INCORRECT);
        }
        if (this.fileSize == null || this.fileSize <= 0) {
            reasons.add(FileValidationReasons.FILE_SIZE);
        }
        if (this.numberOfRecords <= 0) {
            reasons.add(FileValidationReasons.NUMBER_OF_RECORDS_INCORRECT);
        }
        if (this.uri == null || this.uri.isEmpty()) {
            reasons.add(FileValidationReasons.DOWNLOAD_URL_INCORRECT);
        }
        if (marketEvents.getEventName().isBlank()) {
            reasons.add(FileValidationReasons.MARKET_EVENT_MISMATCH);
        }
        if (fileCreatedDate == null) {
            reasons.add(FileValidationReasons.FILE_CREATION_DATE_INVALID);
        }
        if (fileModifiedDate == null || fileModifiedDate.isBefore(fileCreatedDate)) {
            reasons.add(FileValidationReasons.FILE_MODIFICATION_DATE_INVALID);
        }
        if (!reasons.isEmpty()
                && reasons.contains(FileValidationReasons.GIT_FILE_VALIDATION_SUCCESSFUL)
                && reasons.contains(FileValidationReasons.VALID_AT_GIT_READER_APPLICATION)
                && reasons.size() == 2) {
            reasons.add(FileValidationReasons.FINAL_VALIDATION_SUCCESSFUL);
            this.fileDataValidation = FileDateValidationStatus.builder()
                    .fileValidationStatus(Validations.VALIDATED)
                    .reason(reasons)
                    .validationDate(LocalDateTime.now())
                    .build();
        } else {
            reasons.add(FileValidationReasons.FINAL_VALIDATION_FAILED);
            this.fileDataValidation = FileDateValidationStatus.builder()
                    .fileValidationStatus(Validations.INVALID)
                    .reason(reasons)
                    .validationDate(LocalDateTime.now())
                    .build();
        }
    }
}
