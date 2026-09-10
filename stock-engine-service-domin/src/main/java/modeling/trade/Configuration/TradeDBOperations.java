package modeling.trade.Configuration;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import modeling.DTOMapping.DomainMapping;
import modeling.Infra.DBOperations;
import modeling.trade.DTO.TradeOrderDTO;
import modeling.trade.entity.TradeOrder;

@Service(
        value = "TradeDBOperations"
)
public class TradeDBOperations implements DBOperations<TradeOrderDTO, Object, TradeOrderDTO> {

    private static final Logger logger = LoggerFactory.getLogger(TradeDBOperations.class);
    private final TradeOrderRepository tradeOrderRepository;

    public TradeDBOperations(TradeOrderRepository tradeOrderRepository) {
        this.tradeOrderRepository = tradeOrderRepository;
    }

    @Override
    @Transactional 
    public TradeOrderDTO commitIteam(TradeOrderDTO o) {
        logger.info("Committing trade orderId: {}", o.getOrderId());
        TradeOrder tradeOrder = DomainMapping.toEntity(o);
        TradeOrder savedTradeOrder = tradeOrderRepository.save(tradeOrder);
        logger.info("Trade order committed successfully with orderId: {}", savedTradeOrder.getOrderId());
        return DomainMapping.toDto(savedTradeOrder);
    }

    @Override
    public Collection<TradeOrderDTO> findAll() {
        logger.info("Fetching all trade orders from the database");
        return tradeOrderRepository.findAll().stream()
                .map(DomainMapping::toDto)
                .toList();
    }

    @Override
    public Optional<TradeOrderDTO> findBy(Object id) {
        logger.info("Fetching trade order by ID: {}", id);

        if (id instanceof Long orderId) {
            return tradeOrderRepository.findByOrderId(orderId)
                    .map(DomainMapping::toDto);
        } else {
            logger.warn("Invalid ID type provided: {}", id.getClass().getName());
            return Optional.empty();
        }
    }
}
