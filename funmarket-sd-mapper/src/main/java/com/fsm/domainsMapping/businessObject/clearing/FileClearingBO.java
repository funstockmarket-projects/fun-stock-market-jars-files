package com.fsm.domainsMapping.businessObject.clearing;

import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileClearingBO {
    private String fileClearingUuid;
    private String fileUuid;
    private String fileName;
    private String fileValidationStatus;
    private String clearingCode;
    private String clearingMessage;
    private LocalDateTime clearingDate;
    private LocalDateTime modifiedDate;
    private String placeOFModification;
    private RecordStatusBO clearingRecordStatus;
}
