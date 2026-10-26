package modeling.fsmBuckets.configuration;

import modeling.DTOMapping.DomainMapping;
import modeling.fsmBuckets.DTO.FSM_StockDetailsBucketDTO;
import modeling.fsmBuckets.entities.FSM_StockDetailsBucket;
import modeling.Infra.DBOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.transaction.Transactional;
import unExpectedEventHandling.StockEngineUnExpectedEventHandling;

import java.util.List;
import java.util.Optional;

public class StockDetailsBucketDBOperations implements DBOperations<FSM_StockDetailsBucketDTO, Object, Object> {

    private static final Logger log = LoggerFactory.getLogger(StockDetailsBucketDBOperations.class);
    private final StockDetailsBucketRepository bucketRepository;

    public StockDetailsBucketDBOperations(StockDetailsBucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    @Override
    @Transactional
    public FSM_StockDetailsBucketDTO commitIteam(Object o) {
        if (o instanceof FSM_StockDetailsBucketDTO) {
            FSM_StockDetailsBucket map = DomainMapping.stockDetailsBucketMapping((FSM_StockDetailsBucketDTO) o);
            FSM_StockDetailsBucket saved = bucketRepository.save(map);
            log.info("Operation Successfully Completed Details:[ UUID: {}, BucketId: {}, Symbol: {} ]",
                    saved.getBucketUuid(), saved.getBucketId(), saved.getStockSymbol());
            return findBy(saved.getBucketId()).orElseThrow(() -> 
                new StockEngineUnExpectedEventHandling("Failed to fetch committed bucket"));
        } else {
            throw new StockEngineUnExpectedEventHandling("Unable to validate the input");
        }
    }

    @Override
    public List<FSM_StockDetailsBucketDTO> findAll() {
        return bucketRepository.findAll().stream()
                .map(DomainMapping::stockDetailsBucketMapping)
                .toList();
    }

    @Override
    public Optional<FSM_StockDetailsBucketDTO> findBy(Object o) {
        if (o == null) {
            log.error("Parameter for findBy operation is null");
            throw new StockEngineUnExpectedEventHandling("Cannot process a null parameter..");
        }
        if (o instanceof Long) {
            return bucketRepository.findById((Long) o)
                    .map(DomainMapping::stockDetailsBucketMapping);
        } else if (o instanceof String) {
            return bucketRepository.findByBucketUuid((String) o)
                    .map(DomainMapping::stockDetailsBucketMapping);
        } else {
            log.error("Invalid parameter type for findBy operation: {}", o.getClass().getName());
            throw new StockEngineUnExpectedEventHandling("Cannot process the parameter..");
        }
    }

    public FSM_StockDetailsBucketDTO findBySymbolAndExchangeName(String symbol, String exchangeName){

        if(symbol != null && exchangeName != null){ 
            log.info("Finding by stock symbol: [ {} ] and exchangeName: [ {} ]", symbol, exchangeName);
            Optional<FSM_StockDetailsBucket> optional = bucketRepository.findByStockSymbolAndExchangeName(symbol, exchangeName);
            if (optional.isPresent()) {
                return DomainMapping.stockDetailsBucketMapping(optional.get()) ;
            }else{
                return null;
            }

        }else{
            log.warn("Finding by symbol: [ {} ] and exchangeName: [ {} ]", symbol, exchangeName);
            throw new NullPointerException("Some thing went wrong try again symbol: "+symbol+" exchangeName: "+exchangeName);
        }
    }
}