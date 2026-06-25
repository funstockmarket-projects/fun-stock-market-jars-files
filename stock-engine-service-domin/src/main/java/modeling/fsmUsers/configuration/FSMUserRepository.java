package modeling.fsmUsers.configuration;

import modeling.fsmUsers.userEntity.FSM_Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface FSMUserRepository extends JpaRepository<FSM_Users, Long> {

    Optional<FSM_Users> findByUserName(String userName);
    Optional<FSM_Users> findById(long id);
    Optional<FSM_Users> findByEmail(String email);
    Optional<FSM_Users> findByMobileNumber(long mobileNumber);
}
