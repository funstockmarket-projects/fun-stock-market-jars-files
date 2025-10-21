package Modules.CommonModels.model.marketStockData;

import Modules.CommonModels.enums.MarketEvents;
import lombok.*;

import java.time.LocalDateTime;
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

    public boolean isValid(){
        return false;
    }
}
