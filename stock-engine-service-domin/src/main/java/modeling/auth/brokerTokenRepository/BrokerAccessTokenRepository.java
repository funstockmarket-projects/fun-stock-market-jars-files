package modeling.auth.brokerTokenRepository;

import modeling.auth.brokerTokenEntity.BrokerAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository(value = "brokerAccessTokenRepository")
public interface BrokerAccessTokenRepository extends JpaRepository<BrokerAccessToken,Integer> {
    List<BrokerAccessToken> findByBrokerName(String brokerName);
    List<BrokerAccessToken> findByTokenDate(LocalDate localDate);
}
