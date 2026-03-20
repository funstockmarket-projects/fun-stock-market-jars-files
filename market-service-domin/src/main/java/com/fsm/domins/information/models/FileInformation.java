package com.fsm.domins.information.models;

import com.fsm.domins.globalenums.MarketEvents;
import com.fsm.domins.globalenums.Answer;
import com.fsm.domins.globalenums.Days;
import com.fsm.domins.globalenums.RecordStatus;
import com.fsm.domins.globalenums.ProcessingStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "File_Information")
public class FileInformation {

    @Id
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
    private List<LocalDateTime> gitFileCreationDateAndTimeInSubBranch ;
    private List<LocalDateTime> fileMRApprovedDateAndTime ;
    private List<LocalDateTime> fileProcessedDateAndTime ;
    private Map<String, LocalDateTime> fileCreationAndMergedIntoMainBranchDateAndTime ;
    private List<LocalDateTime> fileModifiedInGitHubDateAndTime ;
    private String gitHubFileInToSubBranchPusherName;
    private String fileGithubFileMergedIntoMainBranchMergerName;
    private String fileGithubFileMergedIntoMainBranchMergerEmailId;
    private String gitHubFileInToSubBranchPusherEmailId;
    private Days fileUploadedIntoGitHubSubBranchDay;
    private Days fileUploadedIntoGitHubMainBranchDay;
    private Answer isFileUploadedToGitHubMainBranchInWorkingDay;
    private Answer isFileUploadedToGitHubSubBranchInWorkingDay;
    private Answer isFileModifiedInGitHub;
    private Answer isFileDeletedInGitHub;
    private Answer isFileProcessed;
    private ProcessingStatus fileprocessingStatus;
    private Days fileFirstProcessingDay;
    private Days fileLastProcessingDay;
    private Answer isFileFirstProcessedInWorkingDay;
    private Answer isFileLastProcessedInWorkingDay;
    private int fileProcessingNumberOfCount;
    private String fileProcessingError;
    private Answer isFileCreatedInServer;
    private Answer isFileDeletedInServer;
    private Answer isFileModifiedInServer;
    private boolean fileValidationStatus;
    private MarketEvents eventName;
    private String firstClearingCode;
    private String lastClearingCode;
    private String firstClearingMessage;
    private String lastClearingMessage;
    private long numberOfRecords;
    private String fileInformationPlaceOfCreation;
    private Map<String, LocalDateTime> userNamesOfModifyFileName ;
    private Map<String, LocalDateTime> commitMessagesInSubBranch ;
    private Map<String, LocalDateTime> commitMessagesInMainBranch ;
    private List<LocalDateTime> dateAndTimeFileModifiedInServer ;
    private RecordStatus gitHubFileStatus;
    private LocalDateTime fileInformationRecordCreatedDateAndTime;
    private Map<String, LocalDateTime> fileInformationRecordModifiedDateAndTime ;
    private ProcessingStatus fileInformationStatus;
}
