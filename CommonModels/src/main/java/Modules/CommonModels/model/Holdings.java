package Modules.CommonModels.model;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Holdings {

    private String id;

    private Map<String, MonthlyData> stockData = new HashMap<>();

    private Map<String, MonthlyData> soldStocks = new HashMap<>();
}
