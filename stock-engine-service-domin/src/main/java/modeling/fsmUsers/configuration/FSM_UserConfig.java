package modeling.fsmUsers.configuration;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

@Configuration
public class FSM_UserConfig {

    @Bean
    public FSM_UserDBOperations fsm_UserDBOperations(EntityManager entityManager) {
        JpaRepositoryFactory jpaRepositoryFactory = new JpaRepositoryFactory(entityManager);
        FSMUserRepository fsmUserRepository = jpaRepositoryFactory.getRepository(FSMUserRepository.class);
        return new FSM_UserDBOperations(fsmUserRepository);
    }
}
