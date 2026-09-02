package modeling.fsmBuckets.entities;

import jakarta.persistence.*;
import lombok.Data;
import modeling.fsmBuckets.constants.*;
import modeling.globalEnums.IsStockFoundInMarket;
import modeling.globalEnums.PerformanceStatus;
import modeling.globalEnums.ProcessingStatus;
import modeling.globalEnums.RecordStatus;

import java.time.LocalDateTime;

@Entity
@Table(name="FSM_stock_details_bucket")
@Data
public class FSM_StockDetailsBucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bucketID")
    private long id;
    @Column(name="bucketUUID",unique = true,nullable = false)
    private String bucketUuid;
    @Column(name="stockSymbol",nullable = false)
    private String stockSymbol;
    @Column(name="stockName",nullable = false)
    private String stockName;
    @Column(name="exchangeName",nullable = false,length=10)
    private String exchangeName;
    @Column(name="FSMstockIdentification",nullable = false,unique = true,length=87)
    private String fsmStockIdentification;
    @Column(name="stockAddedThroughUserId",nullable = false)
    private Integer stockAddedThroughId;
    @Column(name="stockRegisterWithTreadOrderId",nullable = false,unique = true)
    private Integer stockRegisterWithThreadOrderId;
    @Column(name="isStockFoundInMarket",nullable = false,length=1)
    @Enumerated(EnumType.STRING)
    private IsStockFoundInMarket isStockFoundInMarket=IsStockFoundInMarket.N;
    @Column(name="listedDateInFSM")
    private LocalDateTime listedDateInFsm;
    @Column(name="listedDayInFSM")
    private String listedDayInFsm;
    @Column(name="FSMstockProcessedDateTime")
    private LocalDateTime fsmStockProcessDateTime;
    @Column(name="FSMstockProcessedDay")
    private String fsmStockProcessDay;
    @Column(name="stockTreadingStartTime",nullable = false)
    private LocalDateTime stockTradingStartTime;
    @Column(name="stockTreadingEndTime",nullable = false)
    private LocalDateTime stockThreadingEndTime;
    @Column(name="capType",nullable = false,length = 10)
    @Enumerated(EnumType.STRING)
    private CapType capType=CapType.SMALL;
    @Column(name="processingStatus",nullable = false,length=15)
    @Enumerated(EnumType.STRING)
    private ProcessingStatus processingStatus=ProcessingStatus.INPOGRESS;
    @Column(name="isStockDelistedMarket",nullable = false,length=15)
    @Enumerated(EnumType.STRING)
    private IsStockDelistedMarket isStockDelistedInMarket=IsStockDelistedMarket.LISTED;
    @Column(name="stockStatusInFSM",nullable = false,length=15)
    @Enumerated(EnumType.STRING)
    private PerformanceStatus stockStatusInFSM= PerformanceStatus.INACTIVE;
    @Column(name="treadingStatus",nullable = false,length=15)
    @Enumerated(EnumType.STRING)
    private PerformanceStatus treadingStatus= PerformanceStatus.INACTIVE;
    @Column(name="recordCreationOrModifiedDateTime",nullable = false)
    @Enumerated(EnumType.STRING)
    private LocalDateTime recordUpdatedDatetime;
    @Column(name="recordStatus",nullable = false,length=10)
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus=RecordStatus.ADDED;

}
