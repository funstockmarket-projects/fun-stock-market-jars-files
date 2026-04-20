package com.fsm.domins.holdings.holdingsOperations;

import com.fsm.domainsMapping.businessObject.holdingsBO.HoldingsBO;
import com.fsm.domins.holdings.mapper.HoldingsMapping;
import com.fsm.domins.holdings.model.Holdings;
import com.fsm.domins.holdings.repository.HoldingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "holdingsServiceOperations")
public class HoldingsServiceOperations {

    private static final Logger log = LoggerFactory.getLogger(HoldingsServiceOperations.class);
    private final HoldingsRepository holdingsRepository;

    public HoldingsServiceOperations(@Qualifier(value = "holdingsRepository") HoldingsRepository holdingsRepository) {
        this.holdingsRepository = holdingsRepository;
    }

    @Transactional
    public HoldingsBO saveHoldings(HoldingsBO holdingsBO){
        if(holdingsBO ==null){
            log.info("HoldingsBO is null. Cannot save holdings.");
            return null;
        }
        Holdings holdings = HoldingsMapping.boToHoldings(holdingsBO);
        holdings = holdingsRepository.save(holdings);
        holdingsBO = HoldingsMapping.HoldingsToBO(holdings);

        log.info("Holdings saved successfully for stockSymbol: {}", holdingsBO.getStockSymbol());
        return holdingsBO;
    }

    public List<HoldingsBO> findByFunMarketAccountId(String funMarketAccountId){
        if(funMarketAccountId == null){
            log.error("FunMarketAccountId is null. Cannot find holdings.");
            return null;
        }
        return holdingsRepository.findByFunMarketAccountId(funMarketAccountId)
                .stream()
                .map(HoldingsMapping::HoldingsToBO)
                .toList();
    }
    public List<HoldingsBO> findByStockSymbol(String stockSymbol){
        if(stockSymbol == null){
            log.error("StockSymbol is null. Cannot find holdings.");
            return null;
        }
        return holdingsRepository.findByStockSymbol(stockSymbol)
                .stream()
                .map(HoldingsMapping::HoldingsToBO)
                .toList();
    }
    public List<HoldingsBO> findByBrokerName(String brokerName){
        if(brokerName == null){
            log.error("brokerName is null. Cannot find holdings.");
            return null;
        }
        return holdingsRepository.findByStockSymbol(brokerName)
                .stream()
                .map(HoldingsMapping::HoldingsToBO)
                .toList();
    }
    public List<HoldingsBO> findByStockExchange(String stockExchange){
        if(stockExchange == null){
            log.error("stockExchange is null. Cannot find holdings.");
            return null;
        }
        return holdingsRepository.findByStockSymbol(stockExchange)
                .stream()
                .map(HoldingsMapping::HoldingsToBO)
                .toList();
    }
    public List<HoldingsBO> findByTradingStatus(String tradingStatus){
        if(tradingStatus == null){
            log.error("tradingStatus is null. Cannot find holdings.");
            return null;
        }
        return holdingsRepository.findByStockSymbol(tradingStatus)
                .stream()
                .map(HoldingsMapping::HoldingsToBO)
                .toList();
    }
    public List<HoldingsBO> findByOrderCategory(String orderCategory){
        if(orderCategory == null){
            log.error("orderCategory is null. Cannot find holdings.");
            return null;
        }
        return holdingsRepository.findByStockSymbol(orderCategory)
                .stream()
                .map(HoldingsMapping::HoldingsToBO)
                .toList();
    }
}
