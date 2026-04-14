package com.fsm.domainsMapping.businessObject.stockDetailsBO;

import com.fsm.domainsMapping.constantsBO.MarketEventsBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadataBO {

    private String fileUUID;
    private String fileName;
    private String folderName;
    private String fileType;
    private Long fileSize;
    private long numberOfRecords;
    private String uri;
    private MarketEventsBO eventNameBO;
    private LocalDateTime fileUploadDate;
    @Builder.Default
    private LocalDateTime fileModifiedDate = LocalDateTime.now();
    private List<Map<String, Object>> fileData;
    private RecordStatusBO recordStatusBO;
    private String validationStatus;
}
