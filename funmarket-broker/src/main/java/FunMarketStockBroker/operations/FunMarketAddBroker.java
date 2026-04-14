package FunMarketStockBroker.operations;

import com.fsm.domins.broker.broketOperations.FunMarketBrokerRetrievalMethods;
import com.fsm.domins.broker.broketOperations.FunMarketSaveBrokerOperations;
import com.fsm.domainsMapping.businessObject.brokerBO.BrokerBO;
import funMarketExceptions.FunMarketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service(value = "funMarketAddBroker")
public class FunMarketAddBroker implements FunMarketBrokerOperations {

    private static final Logger log = LoggerFactory.getLogger(FunMarketAddBroker.class);


    private final FunMarketSaveBrokerOperations funMarketSaveBrokerOperations;
    private final FunMarketBrokerRetrievalMethods funMarketBrokerRetrievalMethods;

    public FunMarketAddBroker(@Qualifier(value = "FunMarketSaveBroker") FunMarketSaveBrokerOperations funMarketSaveBrokerOperations,
                              @Qualifier(value = "FunMarketBrokerRetrievals") FunMarketBrokerRetrievalMethods funMarketBrokerRetrievalMethods) {
        this.funMarketSaveBrokerOperations = funMarketSaveBrokerOperations;
        this.funMarketBrokerRetrievalMethods = funMarketBrokerRetrievalMethods;
    }

    public BrokerBO saveBroker(BrokerBO brokerBO) {

        validateBrokerBO(brokerBO);
        log.info("Adding new broker with identifier: {}", brokerBO.getBrokerIdentifier());
        checkUniqueness(brokerBO.getBrokerIdentifier());

        BrokerBO preparedBO = prepareBrokerBO_UUID(brokerBO);
        BrokerBO savedBO = funMarketSaveBrokerOperations.save(preparedBO);
        log.info("Successfully added broker with [ Name: {}, UUID: {} ]", savedBO.getBrokerName(), savedBO.getBrokerUUid());
        return savedBO;
    }

    private void checkUniqueness(String brokerIdentifier) {
        BrokerBO brokerBO = funMarketBrokerRetrievalMethods.findByBrokerIdentifier(brokerIdentifier);
        if (brokerBO != null) {
            BrokerThrowException("Broker with identifier '" + brokerIdentifier + "' already exists");
        }
    }

    private BrokerBO prepareBrokerBO_UUID(BrokerBO bo) {
        String uuid = UUID.randomUUID().toString();
        bo.setBrokerUUid(uuid);
        return bo;
    }

    @Override
    public BrokerBO modifyBroker(BrokerBO brokerBO) {
        log.error("You cannot modify the broker in [ {} ]", FunMarketAddBroker.class.getSimpleName());
        throw new FunMarketException("You cannot modify the broker in [ " + FunMarketAddBroker.class.getSimpleName() + " ]");
    }
}