package modeling.dailyStockTradingBucket.configuration;

import org.springframework.data.jpa.repository.JpaRepository;

import modeling.dailyStockTradingBucket.entities.FsmStocksBucket;

import java.util.Optional;

public interface FsmStocksBucketRepository extends JpaRepository<FsmStocksBucket, Long> {
    Optional<FsmStocksBucket> findByStockUuid(String stockUuid);
    Optional<FsmStocksBucket> findByStockSymbolAndExchangeName(String stockSymbol, String exchangeName);
}
