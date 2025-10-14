package org.app.gitReader.GitReader.gitRetrivels;

import Modules.CommonModels.enums.MarketEvents;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.rmi.ServerException;
import java.util.List;
import java.util.Map;

import static Modules.CommonModels.enums.helperConstants.*;
import static org.app.gitReader.GitReader.gitRetrivels.CommonRetrievals.allEventsRetrieval;
import static org.app.gitReader.GitReader.helper.helperConstants.*;

@Slf4j
@AllArgsConstructor
public class AllEventsRetrieval {

    static {
        log.info("AllEventsRetrieval Action Retrieval Initialized");
    }

    public Map<String, Map<String, List<Map<String, Object>>>> returnAllEvents() {
        try {
            return Map.of(
                    MarketEvents.WEEKLY.getEventName(), allEventsRetrieval(WEEKLY_URI),
                    MarketEvents.YEARLY.getEventName(), allEventsRetrieval(YEARLY_URI),
                    MarketEvents.MONTHLY.getEventName(), allEventsRetrieval(MONTHLY_URI),
                    MarketEvents.DAILY.getEventName(), allEventsRetrieval(DAILY_URI)
            );
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }
}
