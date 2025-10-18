package Modules.CommonModels.model.marketStockData;

import Modules.CommonModels.enums.Validations;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class FileDateValidationStatus {

    private Validations fileValidationStatus;
    private List<String> reason;
    private LocalDateTime validationDate = LocalDateTime.now();


}
