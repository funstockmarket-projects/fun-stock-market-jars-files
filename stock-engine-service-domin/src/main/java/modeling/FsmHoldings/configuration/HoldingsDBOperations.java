package modeling.FsmHoldings.configuration;

import modeling.DTOMapping.DomainMapping;
import modeling.FsmHoldings.DTO.HoldingsDTO;
import modeling.FsmHoldings.enitity.UserHoldings;
import modeling.Infra.DBOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unExpectedEventHandling.StockEngineUnExpectedEventHandling;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class HoldingsDBOperations implements DBOperations<HoldingsDTO, Object, Object> {

    private final static Logger log = LoggerFactory.getLogger(HoldingsDBOperations.class);
    private final HoldingsRepository holdingsRepository;

    public HoldingsDBOperations(HoldingsRepository holdingsRepository) {
        this.holdingsRepository = holdingsRepository;
    }

    /**
     * Save an entity to the database.
     *
     * @param o The entity to save
     * @return The saved entity (usually with updated fields like generated ID)
     */
    @Override
    public HoldingsDTO commitIteam(Object o) {

        if( o instanceof UserHoldings){

            UserHoldings map = holdingsRepository.save((UserHoldings) o);
            log.info("Operation Successfully Completed Details:[ UUID: {}, Holdings: {} ] ", map.getHoldingUUID(), map.getId());
            return DomainMapping.holdingsMapping(map);
        }else{
            throw new StockEngineUnExpectedEventHandling("Unable to validate the input");
        }
    }

    /**
     * Find all entities of type T.
     *
     * @return A list of all entities in the database
     */
    @Override
    public Collection<HoldingsDTO> findAll() {
        return holdingsRepository.findAll().stream()
                .map(DomainMapping::holdingsMapping)
                .toList();
    }

    /**
     * Find a single entity by its identifier.
     *
     * @param o The identifier to search for
     * @return An Optional containing the entity if found, or empty if not
     */
    @Override
    public Optional<HoldingsDTO> findBy(Object o) {
        if(o instanceof Long ){
           UserHoldings map=  holdingsRepository.findById((long) o).get();
           return Optional.of(DomainMapping.holdingsMapping(map));
        }

        if(o instanceof UUID){
            UserHoldings map=  holdingsRepository.findByHoldingUUID((UUID) o).get();
            return Optional.of(DomainMapping.holdingsMapping(map));
        }
        if(o instanceof String){
            UserHoldings map=  holdingsRepository.findByUserName((String) o).get();
            return Optional.of(DomainMapping.holdingsMapping(map));
        }
        throw new StockEngineUnExpectedEventHandling("Cannot process the parameter..");
    }
}
