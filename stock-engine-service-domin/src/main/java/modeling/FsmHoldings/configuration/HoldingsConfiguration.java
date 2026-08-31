package modeling.FsmHoldings.configuration;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

@Configuration("HoldingsConfiguration")
public class HoldingsConfiguration {

    @Bean
    public HoldingsDBOperations holdingsDBOperations(EntityManager entityManager){
        JpaRepositoryFactory jpaRepositoryFactory = new JpaRepositoryFactory(entityManager);
        HoldingsRepository holdingsRepository = jpaRepositoryFactory.getRepository(HoldingsRepository.class);
        return new HoldingsDBOperations(holdingsRepository);
    }
}
