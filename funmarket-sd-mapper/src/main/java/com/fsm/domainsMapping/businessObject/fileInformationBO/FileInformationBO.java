package com.fsm.domainsMapping.businessObject.fileInformationBO;

import com.fsm.domainsMapping.constantsBO.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInformationBO {

    private String fileInformationUuid;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String fileUri;
    private String fileFolderName;
    private String fileStockDetailsUuid;
    private String fileMRPublisherName;
    private String fileMRApprovedName;
    private LocalDateTime fileMRPublisherDateAndTime;
    private String AssigneesNames;
    private String reviewers;
    private List<LocalDateTime> gitFileCreationDateAndTimeInSubBranch;
    private List<LocalDateTime> fileMRApprovedDateAndTime;
    private List<LocalDateTime> fileProcessedDateAndTime;
    private Map<String, LocalDateTime> fileCreationAndMergedIntoMainBranchDateAndTime;
    private List<LocalDateTime> fileModifiedInGitHubDateAndTime;
    private String gitHubFileInToSubBranchPusherName;
    private String fileGithubFileMergedIntoMainBranchMergerName;
    private String fileGithubFileMergedIntoMainBranchMergerEmailId;
    private String gitHubFileInToSubBranchPusherEmailId;
    private DaysBO fileUploadedIntoGitHubSubBranchDay;
    private DaysBO fileUploadedIntoGitHubMainBranchDay;
    private AnswerBO isFileUploadedToGitHubMainBranchInWorkingDay;
    private AnswerBO isFileUploadedToGitHubSubBranchInWorkingDay;
    private AnswerBO isFileModifiedInGitHub;
    private AnswerBO isFileDeletedInGitHub;
    private AnswerBO isFileProcessed;
    private ProcessingStatusBO fileprocessingStatus;
    private DaysBO fileFirstProcessingDay;
    private DaysBO fileLastProcessingDay;
    private AnswerBO isFileFirstProcessedInWorkingDay;
    private AnswerBO isFileLastProcessedInWorkingDay;
    private int fileProcessingNumberOfCount;
    private String fileProcessingError;
    private AnswerBO isFileCreatedInServer;
    private AnswerBO isFileDeletedInServer;
    private AnswerBO isFileModifiedInServer;
    private boolean fileValidationStatus;
    private MarketEventsBO eventName;
    private String firstClearingCode;
    private String lastClearingCode;
    private String firstClearingMessage;
    private String lastClearingMessage;
    private long numberOfRecords;
    private String fileInformationPlaceOfCreation;
    private Map<String, LocalDateTime> userNamesOfModifyFileName;
    private Map<String, LocalDateTime> commitMessagesInSubBranch;
    private Map<String, LocalDateTime> commitMessagesInMainBranch;
    private List<LocalDateTime> dateAndTimeFileModifiedInServer;
    private RecordStatusBO gitHubFileStatus;
    private LocalDateTime fileInformationRecordCreatedDateAndTime;
    private Map<String, LocalDateTime> fileInformationRecordModifiedDateAndTime;
    private ProcessingStatusBO fileInformationStatus;
    private RecordStatusBO recordStatusBO;
}

