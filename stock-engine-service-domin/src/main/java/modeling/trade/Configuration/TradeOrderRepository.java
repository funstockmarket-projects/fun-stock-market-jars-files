package modeling.trade.Configuration;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import modeling.trade.entity.TradeOrder;

public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {
    
    Optional<TradeOrder> findByOrderId(Long orderId);
    List<TradeOrder> findByUserId(Long userId);
}
