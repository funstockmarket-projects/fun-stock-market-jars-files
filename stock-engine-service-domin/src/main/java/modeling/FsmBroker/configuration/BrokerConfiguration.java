package modeling.FsmBroker.configuration;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

@Configuration("BrokerConfiguration")
public class BrokerConfiguration {

    @Bean
    public BrokerDBOperations brokerDBOperations(EntityManager entityManager) {
        JpaRepositoryFactory jpaRepositoryFactory = new JpaRepositoryFactory(entityManager);
        BrokerRepository brokerRepository = jpaRepositoryFactory.getRepository(BrokerRepository.class);
        return new BrokerDBOperations(brokerRepository);
    }

    @Bean
    public BrokerageChargesDBOperations brokerageChargesDBOperations(EntityManager entityManager) {
        JpaRepositoryFactory jpaRepositoryFactory = new JpaRepositoryFactory(entityManager);
        BrokerageChargesRepository brokerageChargesRepository = jpaRepositoryFactory.getRepository(BrokerageChargesRepository.class);
        return new BrokerageChargesDBOperations(brokerageChargesRepository);
    }
}

