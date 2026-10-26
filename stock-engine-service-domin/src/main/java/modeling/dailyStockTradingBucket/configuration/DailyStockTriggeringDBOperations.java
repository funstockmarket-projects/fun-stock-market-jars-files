package modeling.dailyStockTradingBucket.configuration;

import modeling.DTOMapping.DomainMapping;
import modeling.Infra.DBOperations;
import modeling.dailyStockTradingBucket.DTO.DailyStockTriggeringDTO;
import modeling.dailyStockTradingBucket.entities.DailyStockTriggering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import unExpectedEventHandling.StockEngineUnExpectedEventHandling;

import java.util.List;
import java.util.Optional;

public class DailyStockTriggeringDBOperations implements DBOperations<DailyStockTriggeringDTO, Object, Object> {
    private static final Logger log = LoggerFactory.getLogger(DailyStockTriggeringDBOperations.class);
    private final DailyStockTriggeringRepository repository;

    public DailyStockTriggeringDBOperations(DailyStockTriggeringRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public DailyStockTriggeringDTO commitIteam(Object o) {
        if (o instanceof DailyStockTriggeringDTO dto) {
            DailyStockTriggering entity = repository.save(DomainMapping.dailyStockTriggeringMapping(dto));
            log.info("Committed DailyStockTriggering [ StockId: {} ]", entity.getStockId());
            return findBy(entity.getStockId()).orElseThrow(() -> 
                new StockEngineUnExpectedEventHandling("Failed to retrieve committed daily stock triggering record"));
        }
        throw new StockEngineUnExpectedEventHandling("Invalid input type provided for DailyStockTriggering commit");
    }

    @Override
    public List<DailyStockTriggeringDTO> findAll() {
        return repository.findAll().stream()
                .map(DomainMapping::dailyStockTriggeringMapping)
                .toList();
    }

    @Override
    public Optional<DailyStockTriggeringDTO> findBy(Object o) {
        if (o == null) {
            throw new StockEngineUnExpectedEventHandling("Parameter cannot be null");
        }
        if (o instanceof Long id) {
            return repository.findById(id).map(DomainMapping::dailyStockTriggeringMapping);
        } else if (o instanceof String uuid) {
            return repository.findByStockPriceUpdatedUUID(uuid).map(DomainMapping::dailyStockTriggeringMapping);
        }
        throw new StockEngineUnExpectedEventHandling("Unsupported query parameter type for DailyStockTriggering lookup");
    }
}
