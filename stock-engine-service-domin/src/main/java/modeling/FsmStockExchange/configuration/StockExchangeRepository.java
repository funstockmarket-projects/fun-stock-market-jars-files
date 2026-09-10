package modeling.FsmStockExchange.configuration;

import modeling.FsmStockExchange.entity.StockExchange;
import modeling.globalEnums.PerformanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {

    Optional<StockExchange> findById(long id);
    Optional<StockExchange> findByExchangeUuid(UUID exchangeUuid);
    Optional<StockExchange> findByExchangeName(String exchangeName);
    List<StockExchange> findByExchangeStatus(PerformanceStatus exchangeStatus);
}

