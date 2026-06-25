package modeling.fsmUsers.configuration;

import modeling.fsmUsers.userEntity.FSM_Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unExpectedEventHandling.StockEngineUnExpectedEventHandling;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class FSM_UserDBOperations {

    private static final Logger log = LoggerFactory.getLogger(FSM_UserDBOperations.class);
    private static final LocalDateTime dataTime = LocalDateTime.now();

    private final FSMUserRepository fsmUserRepository;

    FSM_UserDBOperations(FSMUserRepository fsmUserRepository) {
        this.fsmUserRepository = fsmUserRepository;
    }

    public FSM_Users saveUsers(FSM_Users fsm_users) {
        if (fsm_users == null) {
            throw new StockEngineUnExpectedEventHandling("Cannot save the user, input object is null");
        }

        FSM_Users fsmUsers = fsmUserRepository.save(fsm_users);
        log.info("successfully saved user details: [ userName: {}, dataTime: {} ]", fsmUsers.getUserName(), dataTime);

        return fsmUsers;
    }

    public List<FSM_Users> findAllUsers() {
        log.info("Finding all users.");

        List<FSM_Users> fsmUsers = fsmUserRepository.findAll();

        log.info("Found total user:[ total users: {}, dataTime: {} ]", fsmUsers.size(), dataTime);
        return fsmUsers.isEmpty() ? null : fsmUsers;
    }

    public FSM_Users findByUserName(String userName) {
        if (userName == null) {
            throw new StockEngineUnExpectedEventHandling("Searching with empty user name");
        }

        Optional<FSM_Users> user = fsmUserRepository.findByUserName(userName);

        if (user.isPresent()) {
            log.info("User found with details: [ userName: {}, timeData: {} ]", user.get().getUserName(), dataTime);
            return user.get();
        } else {
            log.warn("user not found with details: [username: {}, dataTime: {} ]", userName, dataTime);
            return null;
        }
    }

    public FSM_Users findById(long id) {
        if (id < 0) {
            throw new StockEngineUnExpectedEventHandling("Invalid userId");
        }
        Optional<FSM_Users> user = fsmUserRepository.findById(id);

        if (user.isPresent()) {
            log.info("User found with details: [ id: {}, timeData: {} ]", user.get().getId(), dataTime);
            return user.get();
        } else {
            log.warn("user not found with details: [ id: {}, dataTime: {} ]", id, dataTime);
            return null;
        }
    }

    public FSM_Users findByEmail(String email) {
        if (email.isBlank()) {
            throw new StockEngineUnExpectedEventHandling("Invalid EmailId");
        }
        Optional<FSM_Users> user = fsmUserRepository.findByEmail(email);

        if (user.isPresent()) {
            log.info("User found with details: [ email: {}, timeData: {} ]", user.get().getEmail(), dataTime);
            return user.get();
        } else {
            log.warn("user not found with details: [ email: {}, dataTime: {} ]", email, dataTime);
            return null;
        }
    }

    public FSM_Users findByMobileNumber(long mobileNumber){
        Optional<FSM_Users> userDetails = fsmUserRepository.findByMobileNumber(mobileNumber);

        if(userDetails.isPresent()){
            log.info("User found with details: [ mobileNumber: {}, timeData: {} ]", userDetails.get().getMobileNumber(), dataTime);
            return userDetails.get();
        } else {
            log.warn("user not found with details: [ mobileNumber: {}, dataTime: {} ]", mobileNumber, dataTime);
            return null;
        }
    }

}
