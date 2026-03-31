package stockBroker;

import com.fsm.domins.broker.broketOperations.FunMarketBrokerRetrievalMethods;
import com.fsm.domins.broker.broketOperations.FunMarketSaveBrokerOperations;
import com.fsm.domins.broker.models.BrokerBO;
import com.fsm.domins.globalenums.RecordStatus;
import funMarketExceptions.FunMarketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component(value = "FunMarketModifyBroker")
public final class FunMarketModifyBroker implements FunMarketBrokerOperations {

    private static final Logger log = LoggerFactory.getLogger(FunMarketModifyBroker.class);

    private final FunMarketSaveBrokerOperations funMarketSaveBrokerOperations;
    private final FunMarketBrokerRetrievalMethods funMarketBrokerRetrievalMethods;

    public FunMarketModifyBroker(@Qualifier(value = "FunMarketSaveBroker") FunMarketSaveBrokerOperations funMarketSaveBrokerOperations,
                                 @Qualifier(value = "FunMarketBrokerRetrievals") FunMarketBrokerRetrievalMethods funMarketBrokerRetrievalMethods) {
        this.funMarketSaveBrokerOperations = funMarketSaveBrokerOperations;
        this.funMarketBrokerRetrievalMethods = funMarketBrokerRetrievalMethods;
    }

    public BrokerBO modifyBroker(BrokerBO brokerBO) {

        modifyBrokerValidation(brokerBO);

        final String brokerName = brokerBO.getBrokerIdentifier();
        final String brokerUUid = brokerBO.getBrokerUUid();
        log.info("Modifying the broket with identifier: {}", brokerName);

        BrokerBO existingBroker = funMarketBrokerRetrievalMethods.findByBrokerIdentifier(brokerName);
        if (!StringUtils.pathEquals(brokerUUid, existingBroker.getBrokerUUid())) {
            BrokerThrowException("Broker UUID is not equal.Modification Fail..");
        } else {
            prepareBrokerBO(brokerBO);
        }
        return saveBroker(brokerBO);
    }

    private static void prepareBrokerBO(BrokerBO brokerBO) {
        RecordStatus status = RecordStatus.MODIFIED;
        brokerBO.setRecordStatus(status);
    }

    private void modifyBrokerValidation(BrokerBO brokerBO) {
        if (!Objects.isNull(brokerBO)) {
            if (!StringUtils.hasText(brokerBO.getBrokerName())) {
                BrokerThrowException("Broker name cannot be null");
            }
            if (!StringUtils.hasText(brokerBO.getBrokerUUid())) {
                BrokerThrowException("Broker UUID cannot be null");
            }
        } else {
            BrokerThrowException("BrokerBO cannot be null");
        }
    }

    @Override
    public BrokerBO saveBroker(BrokerBO brokerBO) {

        if(!brokerBO.getRecordStatus().equals(RecordStatus.MODIFIED)){
            log.error("You cannot save directly in [ {} ]", FunMarketModifyBroker.class.getSimpleName());
            throw new FunMarketException("You can not modify the broker in [ "+ FunMarketModifyBroker.class.getSimpleName()+" ]");
        }
        brokerBO = funMarketSaveBrokerOperations.save(brokerBO);
        log.info("Successfully Modified Broker with details:[ Broker Name: {}, Broker UUID: {} ]", brokerBO.getBrokerIdentifier(), brokerBO.getBrokerUUid());
        return brokerBO;
    }
}
