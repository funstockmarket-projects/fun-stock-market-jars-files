package Modules.CommonModels.model;

import Modules.CommonModels.pojo.FileName;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Setter
@Getter
public class StockEvents {
    static {
        log.info("Stock Market Events Initialized");
    }

    private String Id;

    private  Map<String, Map<String, List<Map<String, Object>>>> Events;


}