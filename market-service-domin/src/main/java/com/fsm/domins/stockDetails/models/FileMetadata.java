package com.fsm.domins.stockDetails.models;

import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domins.globalenums.RecordStatus;
import com.fsm.domins.globalenums.MarketEvents;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(value = "file_data")
public record FileMetadata(

        @Id
        String fileUUID,
        String fileName,
        String folderName,
        String fileType,
        Long fileSize,
        long numberOfRecords,
        String uri,
        MarketEvents eventName,
        LocalDateTime fileUploadDate,
        LocalDateTime fileModifiedDate,
        List<Map<String, Object>> fileData,
        RecordStatus recordStatus,
        String validationStatus,
        String validationMessage,
        String fileInformationUUID,
        RecordStatus fileInformationRecordStatus) {
}
