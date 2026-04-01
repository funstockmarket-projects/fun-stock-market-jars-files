package stockBroker;

import com.fsm.domins.broker.broketOperations.FunMarketBrokerRemoveMethods;
import com.fsm.dominsMapping.businessObject.brokerBO.BrokerBO;
import funMarketExceptions.FunMarketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(value = "FunMarketRemoveBroker")
public final class FunMarketRemoveBroker implements FunMarketBrokerOperations {

    private static final Logger log = LoggerFactory.getLogger(FunMarketRemoveBroker.class);

    private final FunMarketBrokerRemoveMethods funMarketBrokerRemoveMethods;

    public FunMarketRemoveBroker(@Qualifier(value = "FunMarketDeleteFunMarketBroker") FunMarketBrokerRemoveMethods funMarketBrokerRemoveMethods) {
        this.funMarketBrokerRemoveMethods = funMarketBrokerRemoveMethods;
    }

    public void removeBroker(String brokerIdentifier) {
        if (brokerIdentifier.isBlank()) {
            log.error("brokerIdentifier cannot be null");
            throw new FunMarketException("brokerIdentifier cannot be null");
        }

        this.funMarketBrokerRemoveMethods.RemoveBrokerByIdentifier(brokerIdentifier);
        log.info("broker Removed successfully with [ brokerIdentifier: {} ]", brokerIdentifier);
    }

    @Override
    public BrokerBO saveBroker(BrokerBO brokerBO) {
        log.error("You cannot save the broker in [ {} ]", FunMarketRemoveBroker.class.getSimpleName());
        throw new FunMarketException("You can not save the broker in [ "+ FunMarketRemoveBroker.class.getSimpleName()+" ]");
    }

    @Override
    public BrokerBO modifyBroker(BrokerBO brokerBO) {
        log.error("You cannot modify the broker in [ {} ]", FunMarketRemoveBroker.class.getSimpleName());
        throw new FunMarketException("You can not modify the broker in [ "+ FunMarketRemoveBroker.class.getSimpleName()+" ]");
    }
}
