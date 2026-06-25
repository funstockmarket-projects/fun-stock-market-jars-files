package modeling.auth.brokerTokenEntity;

import jakarta.persistence.*;
import lombok.Data;
import modeling.auth.brokerTokenEntity.enums.TokenStatus;
import modeling.globalEnums.RecordStatus;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "brokerAccessToken")
@Data
public class BrokerAccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenId", nullable = false)
    private Integer tokenId;

    @Column(name = "uuid", length = 36, nullable = false, insertable = false, updatable = false)
    @Generated(event = EventType.INSERT)
    private String uuid;

    @Column(name = "brokerName", length = 50, nullable = false)
    private String brokerName;

    @Column(name = "accessToken", length = 1300, nullable = false)
    private String accessToken;

    @Column(name = "tokenDate", nullable = false, updatable = false)
    private LocalDate tokenDate = LocalDate.now();

    @Column(name = "tokenExpiryDate")
    private LocalDate tokenExpiryDate;

    @Column(name = "tokenDay", nullable = false)
    private Integer tokenDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "tokenStatus", nullable = false)
    private TokenStatus tokenStatus = TokenStatus.EXPIRED;

    @Enumerated(EnumType.STRING)
    @Column(name = "recordStatus", nullable = false)
    private RecordStatus recordStatus = RecordStatus.ADDED;

    @Column(name = "recordCreatedOrModifiedDateTime")
    private LocalDateTime recordCreatedOrModifiedDateTime;

}
