package com.fsm.domins.stockDetails.models;

import com.fsm.domins.globalenums.MarketEvents;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "file_data")
public class StockFileDetails {

    @Id
    private String fileUUID;
    private String fileName;
    private String folderName;
    private String fileType;
    private Long fileSize;
    private long numberOfRecords;
    private String uri;
    private MarketEvents eventName;
    private LocalDateTime fileUploadDate;
    private LocalDateTime fileModifiedDate = LocalDateTime.now();
    private List<Map<String, Object>> fileData;
}
