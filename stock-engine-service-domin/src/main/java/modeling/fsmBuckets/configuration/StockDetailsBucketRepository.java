package modeling.fsmBuckets.configuration;

import modeling.fsmBuckets.entities.FSM_StockDetailsBucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockDetailsBucketRepository extends JpaRepository<FSM_StockDetailsBucket, Long> {

    Optional<FSM_StockDetailsBucket> findById(Long bucketId);
    Optional<FSM_StockDetailsBucket> findByBucketUuid(String bucketUuid);
    Optional<FSM_StockDetailsBucket> findByStockSymbolAndExchangeName(String stockSymbol, String exchangeName);
}