package modeling.trade.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import jakarta.persistence.EntityManager;

@Configuration (
        value = "TradeConfiguration"
)
public class TradeConfiguration {
        
        @Bean
        public TradeDBOperations tradeDBOperations(EntityManager entityManager) {
                JpaRepositoryFactory jpaRepositoryFactory = new JpaRepositoryFactory(entityManager);
                TradeOrderRepository tradeOrderRepository = jpaRepositoryFactory.getRepository(TradeOrderRepository.class);
                return new TradeDBOperations(tradeOrderRepository);
        }

        @Bean 
        public ClearingDBOperations clearingDBOperations(EntityManager entityManager) {
                JpaRepositoryFactory jpaRepositoryFactory = new JpaRepositoryFactory(entityManager);
                ClearingRepository clearingRepository = jpaRepositoryFactory.getRepository(ClearingRepository.class);
                return new ClearingDBOperations(clearingRepository);
        }


}
