package FunMarketStockBroker.operations;

import com.fsm.domainsMapping.businessObject.brokerBO.BrokerBO;
import funMarketExceptions.FunMarketException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component(value = "FunMarketBrokerOperations")
public interface FunMarketBrokerOperations {

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
        if (Objects.isNull(brokerBO.getDepositoryBO())) {
            BrokerThrowException("Depository cannot be null");
        }
        if (Objects.isNull(brokerBO.getTypeBO())) {
            BrokerThrowException("Broker type cannot be null");
        }
        if (Objects.isNull(brokerBO.getSectorBO())) {
            BrokerThrowException("Sector cannot be null");
        }
    }

    default void BrokerThrowException(String message) {
        throw new FunMarketException(message);
    }
}
