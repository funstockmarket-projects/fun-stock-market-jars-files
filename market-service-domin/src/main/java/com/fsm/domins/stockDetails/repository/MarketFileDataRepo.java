package com.fsm.domins.stockDetails.repository;

import com.fsm.domins.stockDetails.models.StockFileDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value="MarketFileDataRepo")
public interface MarketFileDataRepo extends MongoRepository<StockFileDetails, String> {
     Optional<StockFileDetails> findByFileName(String fileName);
     Optional<StockFileDetails> findByFileUUID(String id);
}
