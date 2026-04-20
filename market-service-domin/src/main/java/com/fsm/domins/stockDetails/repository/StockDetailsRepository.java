package com.fsm.domins.stockDetails.repository;

import com.fsm.domins.stockDetails.models.StockDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "stockDetailsRepository")
public interface StockDetailsRepository extends MongoRepository<StockDetails, String> {
    Optional<StockDetails> findByStockId(String stockId);
    Optional<StockDetails> findByStockSymbol(String stockSymbol);
    Optional<StockDetails> findByStockName(String stockName);
}
