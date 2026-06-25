package modeling.fsmUsers.userEntity;

import jakarta.persistence.*;
import lombok.Data;
import modeling.fsmUsers.constants.AccountType;
import modeling.fsmUsers.constants.UserActiveStatusCode;
import modeling.fsmUsers.constants.UserStatus;
import modeling.fsmUsers.constants.UserType;

import java.time.LocalDateTime;

@Table(name = "funMarketUsers", schema = "ADMIN")
@Entity
@Data
public class FSM_Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private long id;
    @Column(name = "userUUID", unique = true)
    private String userUuid;
    @Column(name = "FSMPolicyID", nullable = false)
    private String funmarketPolicyId = "POLICY001";
    @Column(name = "userName", unique = true, nullable = false)
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "mobileNumber", unique = true, nullable = false)
    private Long mobileNumber;
    @Column(name = "userStatus", length = 20)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;
    @Column(name = "userType", length = 20)
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.STOCK_HOLDER;
    @Column(name = "accountType", length = 20)
    @Enumerated(EnumType.STRING)
    private AccountType accountType = AccountType.FREE_USER;
    @Column(name = "userActiveStatusCode", length = 5)
    @Enumerated(EnumType.STRING)
    private UserActiveStatusCode userActiveStatusCode = UserActiveStatusCode.A;
    @Column(name = "accountOpeningTime")
    private LocalDateTime accountOpeningTime;
    @Column(name = "accountClosingDateTime")
    private LocalDateTime accountClosingDateTime;
    @Column(name = "recordCreatedOrModifiedDateTime", nullable = false)
    private LocalDateTime recordCreatedOrModifiedDateTime;
    @Column(name = "recordStatus", length = 10)
    private String recordStatus = "ADDED";
}
