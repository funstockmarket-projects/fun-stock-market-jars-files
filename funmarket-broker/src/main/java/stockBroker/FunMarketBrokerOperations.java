package stockBroker;

import com.fsm.domins.broker.models.BrokerBO;
import funMarketExceptions.FunMarketException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component(value = "FunMarketBrokerOperations")
public sealed interface FunMarketBrokerOperations permits FunMarketAddBroker, FunMarketModifyBroker, FunMarketRemoveBroker {

    BrokerBO saveBroker(BrokerBO brokerBO);
    BrokerBO modifyBroker(BrokerBO brokerBO);

    default void validateBrokerBO(BrokerBO brokerBO) {
        if (Objects.isNull(brokerBO)) {
            BrokerThrowException("BrokerBO cannot be null");
        }
        if (!StringUtils.hasText(brokerBO.getBrokerIdentifier())) {
            BrokerThrowException("Broker identifier cannot be null or blank");
        }
        if (!StringUtils.hasText(brokerBO.getBrokerName()) || brokerBO.getBrokerName().isBlank()) {
            BrokerThrowException("Broker name cannot be null or blank");
        }
        if (Objects.isNull(brokerBO.getDepository())) {
            BrokerThrowException("Depository cannot be null");
        }
        if (Objects.isNull(brokerBO.getType())) {
            BrokerThrowException("Broker type cannot be null");
        }
        if (Objects.isNull(brokerBO.getSector())) {
            BrokerThrowException("Sector cannot be null");
        }
    }

    default void BrokerThrowException(String message) {
        throw new FunMarketException(message);
    }
}
