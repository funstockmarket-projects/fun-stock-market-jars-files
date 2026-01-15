package com.fsm.domins.marketEvents.repository;

import com.fsm.domins.globalenums.MarketEvents;
import com.fsm.domins.marketEvents.models.EventData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "MarketEventDataRepo")
public interface MarketEventDataRepo extends MongoRepository<EventData, String> {

    Optional<EventData> findByEventName(MarketEvents eventName);
}
