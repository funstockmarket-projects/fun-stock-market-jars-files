package FunMarketStockBroker.operations;

import com.fsm.domainsMapping.constantsBO.*;
import com.fsm.domins.broker.broketOperations.FunMarketBrokerRetrievalMethods;
import com.fsm.domins.broker.constants.BrokerType;
import com.fsm.domins.broker.constants.Depository;
import com.fsm.domins.broker.constants.Sector;
import com.fsm.domainsMapping.businessObject.brokerBO.BrokerBO;
import funMarketExceptions.FunMarketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

import static FunMarketUtils.Utils.UNKNOWN;

@Service(value = "FunMarketBrokerService")
public class FunMarketBrokerService {

    private static final Logger log = LoggerFactory.getLogger(FunMarketBrokerService.class);

    private final FunMarketBrokerRetrievalMethods funMarketBrokerRetrievalMethods;
    private final Map<String, FunMarketBrokerOperations> funMarketBrokerOperations;

    /**
     * Strategy Pattern:- Instead of picking one, you inject all of them into a Map.
     * You can then pick the one you need at runtime based on a string or variable.
     */
    public FunMarketBrokerService(@Qualifier(value = "FunMarketBrokerRetrievals") FunMarketBrokerRetrievalMethods funMarketBrokerRetrievalMethods,
                                  Map<String, FunMarketBrokerOperations> funMarketBrokerOperations) {

        this.funMarketBrokerRetrievalMethods = funMarketBrokerRetrievalMethods;
        this.funMarketBrokerOperations = funMarketBrokerOperations;
    }

    @Transactional
    public BrokerBO fMBrokerService(Map<String, String> broker) {

        String brokerIdentifier = broker.getOrDefault("brokerIdentifier", " ");

        log.info("Checking for broker, with broker identifier: [ {} ]", brokerIdentifier);
        BrokerBO brokerBO = this.funMarketBrokerRetrievalMethods.findByBrokerIdentifier(brokerIdentifier);

        BrokerBO saveBrokerBO;
        if (Objects.isNull(brokerBO)) {
            log.info("Creating broker, with broker Identifier: [ {} ]", brokerIdentifier);
            saveBrokerBO = fMAddBroker(broker, funMarketBrokerOperations.get("funMarketAddBroker"));
        } else {
            log.info("Broker found, with broker identifier: [ {} ]", brokerIdentifier);
            saveBrokerBO = fMModifyBroker(broker, brokerBO, funMarketBrokerOperations.get("FunMarketModifyBroker"));
        }

        return saveBrokerBO;
    }

    private BrokerBO fMModifyBroker(Map<String, String> broker, BrokerBO existingBrokerBO, FunMarketBrokerOperations funMarketModifyBroker) {

        if (broker.isEmpty() || Objects.isNull(existingBrokerBO)) {
            log.error("Cannot be null: broker input or existing broker");
            throw new FunMarketException("Cannot be null: broker input or existing broker");
        }
        log.info("Prepare modification broker object");
        BrokerBO brokerBO = prepareBroker(broker, existingBrokerBO);

        return funMarketModifyBroker.modifyBroker(brokerBO);
    }


    private BrokerBO fMAddBroker(Map<String, String> broker, FunMarketBrokerOperations funMarketBrokerOperations) {

        if (broker.isEmpty()) {
            log.error("Invalid broker input size: 0");
            throw new FunMarketException("Invalid broker input size: 0");
        }
        log.info("Prepare broker object");
        BrokerBO brokerBO = prepareBroker(broker, new BrokerBO());
        return funMarketBrokerOperations.saveBroker(brokerBO);
    }


    private static BrokerBO prepareBroker(Map<String, String> broker, BrokerBO brokerBO) {

        String brokerName = broker.getOrDefault("brokerName", UNKNOWN);
        String brokerIdentifier = broker.getOrDefault("brokerIdentifier", UNKNOWN);
        String NSE_Code = broker.getOrDefault("NSE_Code", UNKNOWN);
        String BSE_Code = broker.getOrDefault("BSE_Code", UNKNOWN);
        String SEBI_RegNo = broker.getOrDefault("SEBI_RegNo", UNKNOWN);
        DepositoryBO depository = DepositoryBO.valueOf(broker.getOrDefault("depository", DepositoryBO.CDSL.getDepository()));
        BrokerTypeBO type = BrokerTypeBO.valueOf(broker.getOrDefault("type", BrokerTypeBO.FULL_SERVICE.getBrokerType()));
        SectorBO sector = SectorBO.valueOf(broker.getOrDefault("sector", SectorBO.FINANCE.getSectorName()));
        RecordStatusBO recordStatusBO = RecordStatusBO.valueOf(broker.getOrDefault("recordStatus", "ADDED"));
        BrokerStatusBO brokerStatusBO = BrokerStatusBO.valueOf(broker.getOrDefault("brokerStatus", "ACTIVE"));

        brokerBO.setBrokerIdentifier(brokerIdentifier);
        brokerBO.setBrokerName(brokerName);
        brokerBO.setNSE_Code(NSE_Code);
        brokerBO.setBSE_Code(BSE_Code);
        brokerBO.setSEBI_RegNo(SEBI_RegNo);
        brokerBO.setDepositoryBO(DepositoryBO.valueOf(depository.getDepository()));
        brokerBO.setTypeBO(BrokerTypeBO.valueOf(type.getBrokerType()));
        brokerBO.setSectorBO(SectorBO.valueOf(sector.getSectorName()));
        brokerBO.setRecordStatusBO(recordStatusBO);
        brokerBO.setBrokerStatusBO(brokerStatusBO);

        return brokerBO;

    }
}
