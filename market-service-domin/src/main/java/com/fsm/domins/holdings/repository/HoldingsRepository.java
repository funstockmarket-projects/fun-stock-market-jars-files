package com.fsm.domins.holdings.repository;

import com.fsm.domins.holdings.model.Holdings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "holdingsRepository")
public interface HoldingsRepository extends MongoRepository<Holdings, String> {
    List<Holdings> findByFunMarketAccountId(String funMarketAccountId);
    List<Holdings> findByStockSymbol(String stockSymbol);
    List<Holdings> findByBrokerName(String brokerName);
    List<Holdings> findByStockExchange(String stockExchange);
    List<Holdings> findByTradingStatus(String tradingStatus);
    List<Holdings> findByOrderCategory(String orderCategory);
}
