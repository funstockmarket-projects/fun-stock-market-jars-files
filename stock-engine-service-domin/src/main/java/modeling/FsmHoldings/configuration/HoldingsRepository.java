package modeling.FsmHoldings.configuration;

import modeling.FsmHoldings.constants.TypeOfHoldingsAccount;
import modeling.FsmHoldings.enitity.UserHoldings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface HoldingsRepository extends JpaRepository<UserHoldings, Long> {

    Optional<UserHoldings> findById(long l);
    Optional<UserHoldings> findByHoldingUUID(UUID o);
    Optional<UserHoldings> findByUserName(String s);
    List<UserHoldings> findByTypeOfHoldingsAccount(TypeOfHoldingsAccount typeOfHoldingsAccount);
}
