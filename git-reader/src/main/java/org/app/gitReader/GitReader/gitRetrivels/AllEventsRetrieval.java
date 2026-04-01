package org.app.gitReader.GitReader.gitRetrivels;

import com.fsm.dominsMapping.businessObject.stockDetailsBO.StockFileDetailsBO;
import com.fsm.dominsMapping.constantsBO.MarketEventsBO;
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

    public Map<String, Map<String, StockFileDetailsBO>> returnAllEvents() {
        try {
            return Map.of(
                    MarketEventsBO.YEARLY.getEventName(), commonRetrievals.allEventsRetrieval(YEARLY_URI),
                    MarketEventsBO.MONTHLY.getEventName(), commonRetrievals.allEventsRetrieval(MONTHLY_URI),
                    MarketEventsBO.WEEKLY.getEventName(), commonRetrievals.allEventsRetrieval(WEEKLY_URI),
                    MarketEventsBO.DAILY.getEventName(), commonRetrievals.allEventsRetrieval(DAILY_URI)
            );
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }
}
