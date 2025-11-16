package org.app.gitReader.GitReader.gitRetrivels;

import Modules.CommonModels.enums.MarketEvents;
import Modules.CommonModels.model.marketStockData.StockFileDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.ServerException;
import java.util.Map;

import static org.app.gitReader.GitReader.helper.helperConstants.*;

@Slf4j
@Component
public class AllEventsRetrieval {

    @Autowired
    private CommonRetrievals commonRetrievals;

    static {
        log.info("AllEventsRetrieval Action Retrieval Initialized");
    }

    public Map<String, Map<String, StockFileDetails>> returnAllEvents() {
        try {
            return Map.of(
                    MarketEvents.YEARLY.getEventName(), commonRetrievals.allEventsRetrieval(YEARLY_URI),
                    MarketEvents.MONTHLY.getEventName(), commonRetrievals.allEventsRetrieval(MONTHLY_URI),
                    MarketEvents.WEEKLY.getEventName(), commonRetrievals.allEventsRetrieval(WEEKLY_URI),
                    MarketEvents.DAILY.getEventName(), commonRetrievals.allEventsRetrieval(DAILY_URI)
            );
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }
}
