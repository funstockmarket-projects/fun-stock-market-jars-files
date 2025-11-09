package Modules.CommonModels.model.marketStockData;

import Modules.CommonModels.enums.FileValidationReasons;
import Modules.CommonModels.enums.Validations;
import lombok.*;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDateValidationStatus {
    private Validations fileValidationStatus;
    private LocalDateTime validationDate = LocalDateTime.now();
    private List<FileValidationReasons> reason;
}
