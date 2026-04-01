package com.fsm.dominsMapping.businessObject.stockDetailsBO;

import com.fsm.dominsMapping.constantsBO.MarketEventsBO;
import com.fsm.dominsMapping.constantsBO.RecordStatusBO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockFileDetailsBO {

    private String fileUUID;
    private String fileName;
    private String folderName;
    private String fileType;
    private Long fileSize;
    private long numberOfRecords;
    private String uri;
    private MarketEventsBO eventNameBO;
    private LocalDateTime fileUploadDate;
    private LocalDateTime fileModifiedDate = LocalDateTime.now();
    private List<Map<String, Object>> fileData;
    private RecordStatusBO recordStatusBO;
}
