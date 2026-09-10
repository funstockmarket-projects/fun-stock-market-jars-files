package modeling.FsmBroker.configuration;

import jakarta.transaction.Transactional;
import modeling.DTOMapping.DomainMapping;
import modeling.FsmBroker.DTO.BrokerDTO;
import modeling.FsmBroker.entity.Broker;
import modeling.Infra.DBOperations;
import modeling.globalEnums.PerformanceStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unExpectedEventHandling.StockEngineUnExpectedEventHandling;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BrokerDBOperations implements DBOperations<BrokerDTO, Object, Object> {

    private final static Logger log = LoggerFactory.getLogger(BrokerDBOperations.class);
    private final BrokerRepository brokerRepository;

    public BrokerDBOperations(BrokerRepository brokerRepository) {
        this.brokerRepository = brokerRepository;
    }

    /**
     * Save a Broker entity/DTO to the database.
     *
     * @param o The input object (BrokerDTO or Broker) to save
     * @return The mapped BrokerDTO
     */
    @Override
    @Transactional
    public BrokerDTO commitIteam(Object o) {
        if (o instanceof BrokerDTO) {
            Broker entity = DomainMapping.brokerMapping((BrokerDTO) o);
            Broker saved = brokerRepository.save(entity);
            log.info("Operation Successfully Completed Details:[ UUID: {}, BrokerId: {}, Identifier: {} ]",
                    saved.getBrokerUuid(), saved.getId(), saved.getBrokerIdentifier());
            return findBy(saved.getId()).orElse(DomainMapping.brokerMapping(saved));
        } else if (o instanceof Broker) {
            Broker saved = brokerRepository.save((Broker) o);
            log.info("Operation Successfully Completed Details:[ UUID: {}, BrokerId: {}, Identifier: {} ]",
                    saved.getBrokerUuid(), saved.getId(), saved.getBrokerIdentifier());
            return DomainMapping.brokerMapping(saved);
        } else {
            throw new StockEngineUnExpectedEventHandling("Unable to validate the input");
        }
    }

    /**
     * Find all Broker records.
     *
     * @return A collection of BrokerDTOs
     */
    @Override
    public Collection<BrokerDTO> findAll() {
        return brokerRepository.findAll().stream()
                .map(DomainMapping::brokerMapping)
                .toList();
    }

    /**
     * Find a single Broker by identifier (Long id or String brokerIdentifier/brokerUuid/brokerName).
     *
     * @param o The identifier to search for
     * @return An Optional containing the BrokerDTO if found, or empty
     */
    @Override
    public Optional<BrokerDTO> findBy(Object o) {
        if (o == null) {
            log.error("Parameter for findBy operation is null");
            throw new StockEngineUnExpectedEventHandling("Cannot process a null parameter..");
        }
        if (o instanceof Long) {
            return brokerRepository.findById((Long) o)
                    .map(DomainMapping::brokerMapping);
        } else if (o instanceof String) {
            String identifier = (String) o;
            Optional<Broker> byIdentifier = brokerRepository.findByBrokerIdentifier(identifier);
            if (byIdentifier.isPresent()) {
                return byIdentifier.map(DomainMapping::brokerMapping);
            }
            Optional<Broker> byUuid = brokerRepository.findByBrokerUuid(identifier);
            if (byUuid.isPresent()) {
                return byUuid.map(DomainMapping::brokerMapping);
            }
            return brokerRepository.findByBrokerName(identifier)
                    .map(DomainMapping::brokerMapping);
        } else {
            log.error("Invalid parameter type for findBy operation: {}", o.getClass().getName());
            throw new StockEngineUnExpectedEventHandling("Cannot process the parameter..");
        }
    }

    public Optional<BrokerDTO> findByBrokerIdentifier(String brokerIdentifier) {
        return brokerRepository.findByBrokerIdentifier(brokerIdentifier)
                .map(DomainMapping::brokerMapping);
    }

    public Optional<BrokerDTO> findByBrokerUuid(String brokerUuid) {
        return brokerRepository.findByBrokerUuid(brokerUuid)
                .map(DomainMapping::brokerMapping);
    }

    public Optional<BrokerDTO> findByBrokerName(String brokerName) {
        return brokerRepository.findByBrokerName(brokerName)
                .map(DomainMapping::brokerMapping);
    }

    public List<BrokerDTO> findByBrokerStatus(PerformanceStatus brokerStatus) {
        return brokerRepository.findByBrokerStatus(brokerStatus).stream()
                .map(DomainMapping::brokerMapping)
                .toList();
    }
}

