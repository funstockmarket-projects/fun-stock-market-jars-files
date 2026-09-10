package modeling.FsmStockExchange.configuration;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

@Configuration("StockExchangeConfiguration")
public class StockExchangeConfiguration {

    @Bean
    public StockExchangeDBOperations stockExchangeDBOperations(EntityManager entityManager) {
        JpaRepositoryFactory jpaRepositoryFactory = new JpaRepositoryFactory(entityManager);
        StockExchangeRepository stockExchangeRepository = jpaRepositoryFactory.getRepository(StockExchangeRepository.class);
        return new StockExchangeDBOperations(stockExchangeRepository);
    }
}

