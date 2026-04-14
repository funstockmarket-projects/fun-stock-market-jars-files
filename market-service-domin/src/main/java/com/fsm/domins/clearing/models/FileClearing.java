package com.fsm.domins.clearing.models;

import com.fsm.domins.globalenums.RecordStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "file_clearing")
public record FileClearing(
    @Id String fileClearingUuid,
    String fileUuid,
    String fileName,
    String fileValidationStatus,
    String clearingCode,
    String clearingMessage,
    LocalDateTime clearingDate,
    LocalDateTime modifiedDate,
    String placeOFModification,
    RecordStatus clearingRecordStatus
) {}
