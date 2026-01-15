package com.fsm.domins.clearing.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "file_clearing")
public class FileClearing {
    @Id
    private String fileClearingUuid;
    private String fileUuid;
    private String fileName;
    private String fileValidationStatus;
    private String clearingCode;
    private String clearingMessage;
    private LocalDateTime clearingDate;
    private  LocalDateTime modifiedDate;
}