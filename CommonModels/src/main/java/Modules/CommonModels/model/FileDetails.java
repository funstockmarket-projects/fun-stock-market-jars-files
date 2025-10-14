package Modules.CommonModels.model;

import Modules.CommonModels.enums.FileStatus;
import Modules.CommonModels.enums.Validations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDetails implements Serializable {
    static {
        log.info("FileDetails Entity Initialized");
    }

    private String folderName;

    private Long fileId;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private long numberOfRecords;

    public String uri;

    private LocalDateTime localDateTime = LocalDateTime.now();

    private Validations fileValidationStatus;

    private FileStatus fileStatus;

    private List<String> messageAlert;

    public boolean isFileValid() {

        if (this.fileName == null || this.fileName.isBlank()
                ||this.folderName== null || this.folderName.isBlank()
                || this.fileType == null || this.fileType.isBlank()
                || this.fileSize == null || this.fileSize <= 0
                || this.numberOfRecords <= 0) {
            log.warn("FileDetails is null for FileDetails: {}", this);
            return true;
        } else {
            return false;
        }
    }
}
