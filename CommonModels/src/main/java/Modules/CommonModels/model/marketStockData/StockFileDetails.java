package Modules.CommonModels.model.marketStockData;

import Modules.CommonModels.enums.MarketEvents;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class StockFileDetails {

    private String fileId;
    private String fileName;
    private List<Map<String, Object>> fileData;
    private FileDateValidationStatus fileDataValidation;
    private LocalDateTime fileUploadDate;
    private MarketEvents marketEvents;
    private LocalDateTime fileModifiedDate = LocalDateTime.now();

    public boolean isValid(){
        return false;
    }
}
