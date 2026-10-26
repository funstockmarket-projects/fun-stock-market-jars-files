package modeling.dailyStockTradingBucket.configuration;

import modeling.DTOMapping.DomainMapping;
import modeling.Infra.DBOperations;
import modeling.dailyStockTradingBucket.DTO.FsmStocksBucketDTO;
import modeling.dailyStockTradingBucket.entities.FsmStocksBucket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import unExpectedEventHandling.StockEngineUnExpectedEventHandling;

import java.util.List;
import java.util.Optional;

public class FsmStocksBucketDBOperations implements DBOperations<FsmStocksBucketDTO, Object, Object> {
    private static final Logger log = LoggerFactory.getLogger(FsmStocksBucketDBOperations.class);
    private final FsmStocksBucketRepository repository;

    public FsmStocksBucketDBOperations(FsmStocksBucketRepository repository) { this.repository = repository; }

    @Override
    @Transactional
    public FsmStocksBucketDTO commitIteam(Object o) {
        if (o instanceof FsmStocksBucketDTO dto) {
            FsmStocksBucket entity = repository.save(DomainMapping.fsmStocksBucketMapping(dto));
            log.info("Committed FsmStocksBucket ID: {}", entity.getStockId());
            return findBy(entity.getStockId()).orElseThrow(() -> new StockEngineUnExpectedEventHandling("Failed retrieval"));
        }
        throw new StockEngineUnExpectedEventHandling("Invalid payload type");
    }

    @Override
    public List<FsmStocksBucketDTO> findAll() {
        return repository.findAll().stream().map(DomainMapping::fsmStocksBucketMapping).toList();
    }

    @Override
    public Optional<FsmStocksBucketDTO> findBy(Object o) {
        if (o instanceof Long id) return repository.findById(id).map(DomainMapping::fsmStocksBucketMapping);
        if (o instanceof String uuid) return repository.findByStockUuid(uuid).map(DomainMapping::fsmStocksBucketMapping);
        throw new StockEngineUnExpectedEventHandling("Unsupported query type");
    }
}
