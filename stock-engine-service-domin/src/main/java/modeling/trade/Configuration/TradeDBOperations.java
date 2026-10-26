package modeling.trade.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import modeling.DTOMapping.DomainMapping;
import modeling.Infra.DBOperations;
import modeling.trade.DTO.TradeOrderDTO;
import modeling.trade.constants.OrderWindow;
import modeling.trade.entity.TradeOrder;


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

    public List<TradeOrderDTO> findTransaction(String symbol, Long l, Long exchangeId){
        return tradeOrderRepository.findByStockSymbolAndUserIdAndExchangeId(symbol, l, exchangeId)
            .stream()
            .map(DomainMapping::toDto)
            .toList();
    }

     public String getOriginalTradeOrder(String stockSymbol, @NotBlank(message = "user ID is required") Long userId, Long exchangeId) {
        logger.info("retrieving original trade order ID for stock symbol: {}", stockSymbol);
        List<TradeOrderDTO> tradeOrders = findTransaction(stockSymbol, userId, exchangeId);

        return tradeOrders.stream()
                .filter(order -> order.getOrginalTradeOrderUUID() != null && OrderWindow.BUY.equals(order.getOrderWindow()))
                .map(TradeOrderDTO::getOrginalTradeOrderUUID)
                .findFirst()
                .orElse(null);
    }


    public Object findBy(){
        return null;
    }
}
