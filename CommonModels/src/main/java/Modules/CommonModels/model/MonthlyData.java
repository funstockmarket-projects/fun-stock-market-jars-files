package Modules.CommonModels.model;

import Modules.CommonModels.enums.FileStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MonthlyData {

    private Map<String, StockData> monthlyData = new HashMap<>();
    private FileStatus fileStatus;

}
