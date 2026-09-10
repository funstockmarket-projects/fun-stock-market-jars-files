package modeling.FsmBroker.configuration;

import jakarta.transaction.Transactional;
import modeling.DTOMapping.DomainMapping;
import modeling.FsmBroker.DTO.BrokerageChargesDTO;
import modeling.FsmBroker.entity.BrokerageCharges;
import modeling.Infra.DBOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unExpectedEventHandling.StockEngineUnExpectedEventHandling;

import java.util.Collection;
import java.util.Optional;

public class BrokerageChargesDBOperations implements DBOperations<BrokerageChargesDTO, Object, Object> {

    private final static Logger log = LoggerFactory.getLogger(BrokerageChargesDBOperations.class);
    private final BrokerageChargesRepository brokerageChargesRepository;

    public BrokerageChargesDBOperations(BrokerageChargesRepository brokerageChargesRepository) {
        this.brokerageChargesRepository = brokerageChargesRepository;
    }

    /**
     * Save a BrokerageCharges entity/DTO to the database.
     *
     * @param o The input object (BrokerageChargesDTO or BrokerageCharges) to save
     * @return The mapped BrokerageChargesDTO
     */
    @Override
    @Transactional
    public BrokerageChargesDTO commitIteam(Object o) {
        if (o instanceof BrokerageChargesDTO) {
            BrokerageCharges entity = DomainMapping.brokerageChargesMapping((BrokerageChargesDTO) o);
            BrokerageCharges saved = brokerageChargesRepository.save(entity);
            log.info("Operation Successfully Completed Details:[ BrokerId: {}, UUID: {}, Identifier: {} ]",
                    saved.getBrokerId(), saved.getUuid(), saved.getBrokerIdentifier());
            return findBy(saved.getBrokerId()).orElse(DomainMapping.brokerageChargesMapping(saved));
        } else if (o instanceof BrokerageCharges) {
            BrokerageCharges saved = brokerageChargesRepository.save((BrokerageCharges) o);
            log.info("Operation Successfully Completed Details:[ BrokerId: {}, UUID: {}, Identifier: {} ]",
                    saved.getBrokerId(), saved.getUuid(), saved.getBrokerIdentifier());
            return DomainMapping.brokerageChargesMapping(saved);
        } else {
            throw new StockEngineUnExpectedEventHandling("Unable to validate the input");
        }
    }

    /**
     * Find all BrokerageCharges records.
     *
     * @return A collection of BrokerageChargesDTOs
     */
    @Override
    public Collection<BrokerageChargesDTO> findAll() {
        return brokerageChargesRepository.findAll().stream()
                .map(DomainMapping::brokerageChargesMapping)
                .toList();
    }

    /**
     * Find a single BrokerageCharges by identifier (Long brokerId or String brokerIdentifier/uuid).
     *
     * @param o The identifier to search for
     * @return An Optional containing the BrokerageChargesDTO if found, or empty
     */
    @Override
    public Optional<BrokerageChargesDTO> findBy(Object o) {
        if (o == null) {
            log.error("Parameter for findBy operation is null");
            throw new StockEngineUnExpectedEventHandling("Cannot process a null parameter..");
        }
        if (o instanceof Long) {
            return brokerageChargesRepository.findById((Long) o)
                    .map(DomainMapping::brokerageChargesMapping);
        } else if (o instanceof String) {
            String identifier = (String) o;
            Optional<BrokerageCharges> byIdentifier = brokerageChargesRepository.findByBrokerIdentifier(identifier);
            if (byIdentifier.isPresent()) {
                return byIdentifier.map(DomainMapping::brokerageChargesMapping);
            }
            return brokerageChargesRepository.findByUuid(identifier)
                    .map(DomainMapping::brokerageChargesMapping);
        } else {
            log.error("Invalid parameter type for findBy operation: {}", o.getClass().getName());
            throw new StockEngineUnExpectedEventHandling("Cannot process the parameter..");
        }
    }

    public Optional<BrokerageChargesDTO> findByBrokerIdentifier(String brokerIdentifier) {
        return brokerageChargesRepository.findByBrokerIdentifier(brokerIdentifier)
                .map(DomainMapping::brokerageChargesMapping);
    }

    public Optional<BrokerageChargesDTO> findByUuid(String uuid) {
        return brokerageChargesRepository.findByUuid(uuid)
                .map(DomainMapping::brokerageChargesMapping);
    }
}

