package modeling.auth.dbServices;


import modeling.auth.brokerTokenEntity.BrokerAccessToken;
import modeling.auth.brokerTokenRepository.BrokerAccessTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service(value = "accessTokenDBOperations")
public class AccessTokenDBOperations {

    private static final Logger log = LoggerFactory.getLogger(AccessTokenDBOperations.class);
    private static final LocalDate localdate = LocalDate.now();

    @Qualifier(value = "brokerAccessTokenRepository")
    @Autowired
    private BrokerAccessTokenRepository brokerAccessTokenRepository;

    public BrokerAccessToken saveToken(BrokerAccessToken brokerAccessToken) {

        if (brokerAccessToken == null) {
            log.error("The input BrokerAccessToken object cannot be null [ Date: {} ]", localdate);
            throw new IllegalArgumentException("The input BrokerAccessToken object cannot be null [ Date: " + localdate + " ]");
        }

        try {
            BrokerAccessToken savedObj = brokerAccessTokenRepository.save(brokerAccessToken);
            log.info("Token saved successfully in DB [ TokenID: {}, Date: {} ]", savedObj.getTokenId(), localdate);
            return savedObj;
        } catch (Exception e) {
            log.error("Error ocher while saving the token. Retry ofter some time [ Date: {} ]", localdate);
            throw new IllegalArgumentException("Error ocher while saving the token. Retry ofter some time [ Date: " + localdate + " ]");
        }
    }

    public List<BrokerAccessToken> findAllByBrokerName(String brokerName) {

        if (brokerName == null || brokerName.isBlank()) {
            log.error("The input broker name cannot be null [ Date: {} ]", localdate);
            throw new IllegalArgumentException("The input broker name cannot be null [ Date: " + localdate + " ]");
        }
        try {
            List<BrokerAccessToken> brokerTokenTillDates = brokerAccessTokenRepository.findByBrokerName(brokerName);

            if (brokerTokenTillDates.isEmpty()) {
                log.error("No tokens found till Date with broker name : [  {} ] [ Date: {} ]", brokerName, localdate);
                throw new IllegalArgumentException("No tokens found till Date with broker name : [  " + brokerName + " ] [ Date: " + localdate + " ]");
            }
            return brokerTokenTillDates;
        } catch (Exception e) {
            log.error("Error ocher while getting the broker token. Retry ofter some time [ Date: {} ] Error: {}", localdate, e.getMessage());
            throw new IllegalArgumentException("Error ocher while getting the broker token. Retry ofter some time [ Date: " + localdate + " ]");
        }
    }

    public BrokerAccessToken finByTokenDate(LocalDate tokenDate) {

        if (tokenDate == null) {
            log.error("The input tokenDate cannot be null [ Date: {} ]", localdate);
            throw new IllegalArgumentException("The input tokenDate cannot be null [ Date: " + localdate + " ]");
        }

        try {
            List<BrokerAccessToken> brokerAccessTokenOptional = brokerAccessTokenRepository.findByTokenDate(tokenDate);
            if (brokerAccessTokenOptional.isEmpty()) {
                log.error("No broker token found with token date: [ {} ]", tokenDate);
                return null;
            } else {
                return brokerAccessTokenOptional.getFirst();
            }

        } catch (Exception e) {
            log.error("Error ocher while getting the broker token with [ Date: {} ]. Retry ofter some time [ Date: {} ] Error: {}", tokenDate, localdate, e.getMessage());
            throw new IllegalArgumentException("Error ocher while getting the broker token  with [ Date:" + tokenDate + ". Retry ofter some time [ Date: " + localdate + " ]");
        }
    }

    public List<BrokerAccessToken> findAll() {
        try {
            return brokerAccessTokenRepository.findAll();
        } catch (Exception e) {
            log.error("Error ocher while fetching all tokens, [ Date: {} ]", localdate);
            throw new IllegalArgumentException("Error ocher while fetching all tokens, [ Date: " + localdate + "  ]");
        }
    }

    public List<BrokerAccessToken> saveAllTokens(List<BrokerAccessToken> brokerAccessTokens) {
        if (brokerAccessTokens.isEmpty()) {
            log.error("Cannot save an empty object input size 0, [ Date: {} ]", localdate);
            throw new IllegalArgumentException("Cannot save an empty object input size 0 [ Date: " + localdate + "  ]");
        }
        try {
            log.info("Saving all the records [ size: {}, Date {} ]", brokerAccessTokens.size(), localdate);
            List<BrokerAccessToken> saveAllTokens = brokerAccessTokenRepository.saveAll(brokerAccessTokens);

            if (brokerAccessTokens.size() != saveAllTokens.size()) {
                log.info("Cannot match the input record to output record. failed to save records, try again [ Date: {} ]", localdate);
                throw new IllegalArgumentException("Cannot match the input record to output record. failed to save records, try again [ Date: " + localdate + " ]");
            }
            return saveAllTokens;
        } catch (Exception e) {
            log.error("Error while saving all tokens, failed to save tokens, [Date:  {} ]", localdate);
            throw new IllegalArgumentException("Error while saving all tokens, failed to save tokens,[ Date: " + localdate + "  ]");
        }
    }
}
