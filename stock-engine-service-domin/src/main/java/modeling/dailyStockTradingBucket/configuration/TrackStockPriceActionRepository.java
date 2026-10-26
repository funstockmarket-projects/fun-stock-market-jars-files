package modeling.dailyStockTradingBucket.configuration;

import org.springframework.data.jpa.repository.JpaRepository;

import modeling.dailyStockTradingBucket.entities.TrackStockPriceAction;

import java.util.List;
import java.util.Optional;

public interface TrackStockPriceActionRepository extends JpaRepository<TrackStockPriceAction, Long> {
    Optional<TrackStockPriceAction> findByStockPriceActionUUID(String uuid);
    List<TrackStockPriceAction> findByStockId(Long stockId);
}
