package modeling.FsmHoldings.configuration;

import modeling.FsmHoldings.enitity.UserHoldings;

import java.util.List;

public class HoldingsDBOperations {

    private final HoldingsRepository holdingsRepository;

    public HoldingsDBOperations(HoldingsRepository holdingsRepository) {
        this.holdingsRepository = holdingsRepository;
    }

    public List<UserHoldings> findAll() {
        return holdingsRepository.findAll();
    }
}
