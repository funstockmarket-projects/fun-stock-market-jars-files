package modeling.FsmStockExchange.configuration;

import modeling.DTOMapping.DomainMapping;
import modeling.FsmStockExchange.DTO.StockExchangeDTO;
import modeling.FsmStockExchange.entity.StockExchange;
import modeling.Infra.DBOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.transaction.Transactional;
import unExpectedEventHandling.StockEngineUnExpectedEventHandling;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StockExchangeDBOperations implements DBOperations<StockExchangeDTO, Object, Object> {

    private final static Logger log = LoggerFactory.getLogger(StockExchangeDBOperations.class);
    private final StockExchangeRepository stockExchangeRepository;

    public StockExchangeDBOperations(StockExchangeRepository stockExchangeRepository) {
        this.stockExchangeRepository = stockExchangeRepository;
    }

    /**
     * Save a StockExchange entity to the database.
     *
     * @param o The entity to save
     * @return The mapped StockExchangeDTO
     */
    @Override
    @Transactional
    public StockExchangeDTO commitIteam(Object o) {

        if (o instanceof StockExchangeDTO) {
            StockExchange map = DomainMapping.stockExchangeMapping((StockExchangeDTO) o);
            StockExchange saved = stockExchangeRepository.save(map);
            log.info("Operation Successfully Completed Details:[ UUID: {}, ExchangeId: {}, Name: {} ] ",
                    saved.getExchangeUuid(), saved.getId(), saved.getExchangeName());
            return findBy(saved.getId()).get();
        } else {
            throw new StockEngineUnExpectedEventHandling("Unable to validate the input");
        }
    }

    /**
     * Find all StockExchange records.
     *
     * @return A collection of StockExchangeDTOs
     */
    @Override
    public List<StockExchangeDTO> findAll() {
        return stockExchangeRepository.findAll().stream()
                .map(DomainMapping::stockExchangeMapping)
                .toList();
    }

    /**
     * Find a single StockExchange by identifier (Long id or String exchangeName/exchangeUuid).
     *
     * @param o The identifier to search for
     * @return An Optional containing the StockExchangeDTO if found, or empty
     */
    @Override
    public Optional<StockExchangeDTO> findBy(Object o) {
        if(o == null) {
            log.error("Parameter for findBy operation is null");
            throw new StockEngineUnExpectedEventHandling("Cannot process a null parameter..");
        }
        if (o instanceof Long) {
            return stockExchangeRepository.findById((Long) o)
                    .map(DomainMapping::stockExchangeMapping);
        }else if (o instanceof String) {
            String identifier = (String) o;
            Optional<StockExchange> byName = stockExchangeRepository.findByExchangeName(identifier);
            if (byName.isPresent()) {
                return byName.map(DomainMapping::stockExchangeMapping);
            }
            return stockExchangeRepository.findByExchangeName(identifier)
                    .map(DomainMapping::stockExchangeMapping);
        }else if(o instanceof UUID) {
            return stockExchangeRepository.findByExchangeUuid((UUID) o)
                    .map(DomainMapping::stockExchangeMapping);
        }else {
            log.error("Invalid parameter type for findBy operation: {}", o.getClass().getName());
            throw new StockEngineUnExpectedEventHandling("Cannot process the parameter..");
        }
    }
}

