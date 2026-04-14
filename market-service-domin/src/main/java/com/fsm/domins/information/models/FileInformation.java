package com.fsm.domins.information.models;

import com.fsm.domins.globalenums.MarketEvents;
import com.fsm.domins.globalenums.Answer;
import com.fsm.domins.globalenums.Days;
import com.fsm.domins.globalenums.RecordStatus;
import com.fsm.domins.globalenums.ProcessingStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "File_Information")
public record FileInformation(

    @Id
    String fileInformationUuid,
    String fileName,
    String fileType,
    Long fileSize,
    String fileUri,
    String fileFolderName,
    String fileStockDetailsUuid,
    String fileMRPublisherName,
    String fileMRApprovedName,
    LocalDateTime fileMRPublisherDateAndTime,
    String AssigneesNames,
    String reviewers,
    List<LocalDateTime> gitFileCreationDateAndTimeInSubBranch,
    List<LocalDateTime> fileMRApprovedDateAndTime,
    List<LocalDateTime> fileProcessedDateAndTime,
    Map<String, LocalDateTime> fileCreationAndMergedIntoMainBranchDateAndTime,
    List<LocalDateTime> fileModifiedInGitHubDateAndTime,
    String gitHubFileInToSubBranchPusherName,
    String fileGithubFileMergedIntoMainBranchMergerName,
    String fileGithubFileMergedIntoMainBranchMergerEmailId,
    String gitHubFileInToSubBranchPusherEmailId,
    Days fileUploadedIntoGitHubSubBranchDay,
    Days fileUploadedIntoGitHubMainBranchDay,
    Answer isFileUploadedToGitHubMainBranchInWorkingDay,
    Answer isFileUploadedToGitHubSubBranchInWorkingDay,
    Answer isFileModifiedInGitHub,
    Answer isFileDeletedInGitHub,
    Answer isFileProcessed,
    ProcessingStatus fileprocessingStatus,
    Days fileFirstProcessingDay,
    Days fileLastProcessingDay,
    Answer isFileFirstProcessedInWorkingDay,
    Answer isFileLastProcessedInWorkingDay,
    int fileProcessingNumberOfCount,
    String fileProcessingError,
    Answer isFileCreatedInServer,
    Answer isFileDeletedInServer,
    Answer isFileModifiedInServer,
    boolean fileValidationStatus,
    MarketEvents eventName,
    String firstClearingCode,
    String lastClearingCode,
    String firstClearingMessage,
    String lastClearingMessage,
    long numberOfRecords,
    String fileInformationPlaceOfCreation,
    Map<String, LocalDateTime> userNamesOfModifyFileName,
    Map<String, LocalDateTime> commitMessagesInSubBranch,
    Map<String, LocalDateTime> commitMessagesInMainBranch,
    List<LocalDateTime> dateAndTimeFileModifiedInServer,
    RecordStatus gitHubFileStatus,
    LocalDateTime fileInformationRecordCreatedDateAndTime,
    Map<String, LocalDateTime> fileInformationRecordModifiedDateAndTime,
    ProcessingStatus fileInformationStatus,
    RecordStatus recordStatus
) {}
