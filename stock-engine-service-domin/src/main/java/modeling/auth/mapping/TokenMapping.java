package modeling.auth.mapping;

import com.fsm.domainsMapping.businessObject.stockEngineMapping.authBO.brokerTokenEntityBO.BrokerAccessTokenBO;
import com.fsm.domainsMapping.businessObject.stockEngineMapping.authBO.constantsBO.TokenStatusBO;
import modeling.auth.brokerTokenEntity.BrokerAccessToken;

public class TokenMapping {

    public static BrokerAccessTokenBO brokerAccessTokenToBO(BrokerAccessToken brokerAccessToken) {
        BrokerAccessTokenBO brokerAccessTokenBO = new BrokerAccessTokenBO();
        brokerAccessTokenBO.setTokenId(brokerAccessToken.getTokenId());
        brokerAccessTokenBO.setBrokerName(brokerAccessToken.getBrokerName());
        brokerAccessTokenBO.setAccessToken(brokerAccessToken.getAccessToken());
        brokerAccessTokenBO.setTokenDate(brokerAccessToken.getTokenDate());
        brokerAccessTokenBO.setTokenExpiryDate(brokerAccessToken.getTokenExpiryDate());
        brokerAccessTokenBO.setTokenDay(brokerAccessToken.getTokenDay());
        brokerAccessTokenBO.setTokenStatus(TokenStatusBO.valueOf(brokerAccessToken.getTokenStatus().getValue()));
        return brokerAccessTokenBO;
    }
}
