package modeling.dailyStockTradingBucket.configuration;

import org.springframework.data.jpa.repository.JpaRepository;

import modeling.dailyStockTradingBucket.entities.DailyStockTriggering;

import java.util.Optional;

public interface DailyStockTriggeringRepository extends JpaRepository<DailyStockTriggering, Long> {
    Optional<DailyStockTriggering> findByStockPriceUpdatedUUID(String uuid);
}