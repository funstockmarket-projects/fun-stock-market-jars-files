package modeling.trade.Configuration;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import modeling.globalEnums.ProcessingStatus;
import modeling.globalEnums.YesOrNoStatusFlag;
import modeling.trade.entity.ClearingTradeOrder;

public interface ClearingRepository extends JpaRepository<ClearingTradeOrder, Long> {
    Optional<ClearingTradeOrder> findByClearingId(Long clearingId);
    List<ClearingTradeOrder> findByUserId(Long userId);
    List<ClearingTradeOrder> findByClearingStatusCode(ProcessingStatus clearingStatusCode);
    List<ClearingTradeOrder> findByIsRejectedTrade(YesOrNoStatusFlag isRejectedTrade);
}
