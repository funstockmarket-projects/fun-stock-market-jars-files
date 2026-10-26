package modeling.dailyStockTradingBucket.configuration;


import modeling.DTOMapping.DomainMapping;
import modeling.Infra.DBOperations;
import modeling.dailyStockTradingBucket.DTO.TrackStockPriceActionDTO;
import modeling.dailyStockTradingBucket.entities.TrackStockPriceAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import unExpectedEventHandling.StockEngineUnExpectedEventHandling;

import java.util.List;
import java.util.Optional;

public class TrackStockPriceActionDBOperations implements DBOperations<TrackStockPriceActionDTO, Object, Object> {
    private static final Logger log = LoggerFactory.getLogger(TrackStockPriceActionDBOperations.class);
    private final TrackStockPriceActionRepository repository;

    public TrackStockPriceActionDBOperations(TrackStockPriceActionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public TrackStockPriceActionDTO commitIteam(Object o) {
        if (o instanceof TrackStockPriceActionDTO dto) {
            TrackStockPriceAction entity = repository.save(DomainMapping.trackStockPriceActionMapping(dto));
            log.info("Committed TrackStockPriceAction [ PriceActionID: {}, StockId: {} ]", entity.getPriceActionID(), entity.getStockId());
            return findBy(entity.getPriceActionID()).orElseThrow(() -> 
                new StockEngineUnExpectedEventHandling("Failed to retrieve committed price action record"));
        }
        throw new StockEngineUnExpectedEventHandling("Invalid input type provided for TrackStockPriceAction commit");
    }

    @Override
    public List<TrackStockPriceActionDTO> findAll() {
        return repository.findAll().stream()
                .map(DomainMapping::trackStockPriceActionMapping)
                .toList();
    }

    @Override
    public Optional<TrackStockPriceActionDTO> findBy(Object o) {
        if (o == null) {
            throw new StockEngineUnExpectedEventHandling("Parameter cannot be null");
        }
        if (o instanceof Long id) {
            return repository.findById(id).map(DomainMapping::trackStockPriceActionMapping);
        } else if (o instanceof String uuid) {
            return repository.findByStockPriceActionUUID(uuid).map(DomainMapping::trackStockPriceActionMapping);
        }
        throw new StockEngineUnExpectedEventHandling("Unsupported query parameter type for TrackStockPriceAction lookup");
    }
}
