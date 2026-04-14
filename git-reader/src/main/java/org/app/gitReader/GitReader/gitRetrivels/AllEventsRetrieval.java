package org.app.gitReader.GitReader.gitRetrivels;


import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.MarketEventsBO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.rmi.ServerException;
import java.util.Map;

import static org.app.gitReader.GitReader.helper.helperConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllEventsRetrieval {

    private final CommonRetrievals commonRetrievals;

    static {
        log.info("AllEventsRetrieval Action Retrieval Initialized");
    }

    public Map<String, Map<String, FileMetadataBO>> returnAllEvents() {
        try {
            return Map.of(
//                    MarketEventsBO.YEARLY.getEventName(), commonRetrievals.allEventsRetrieval(YEARLY_URI)
//                    MarketEventsBO.MONTHLY.getEventName(), commonRetrievals.allEventsRetrieval(MONTHLY_URI),
//                    MarketEventsBO.WEEKLY.getEventName(), commonRetrievals.allEventsRetrieval(WEEKLY_URI),
                    MarketEventsBO.DAILY.getEventName(), commonRetrievals.allEventsRetrieval(DAILY_URI)
            );
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }
}