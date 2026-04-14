package com.fsm.domins.information.mapper;

import com.fsm.domins.information.models.FileInformation;
import com.fsm.domins.globalenums.MarketEvents;
import com.fsm.domins.globalenums.Answer;
import com.fsm.domins.globalenums.Days;
import com.fsm.domins.globalenums.ProcessingStatus;
import com.fsm.domins.globalenums.RecordStatus;
import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domainsMapping.constantsBO.MarketEventsBO;
import com.fsm.domainsMapping.constantsBO.AnswerBO;
import com.fsm.domainsMapping.constantsBO.DaysBO;
import com.fsm.domainsMapping.constantsBO.ProcessingStatusBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;

public class FileInformationMapper {

    public static FileInformation bOToFileInformation(FileInformationBO bo) {
        return new FileInformation(
                bo.getFileInformationUuid(),
                bo.getFileName(),
                bo.getFileType(),
                bo.getFileSize(),
                bo.getFileUri(),
                bo.getFileFolderName(),
                bo.getFileStockDetailsUuid(),
                bo.getFileMRPublisherName(),
                bo.getFileMRApprovedName(),
                bo.getFileMRPublisherDateAndTime(),
                bo.getAssigneesNames(),
                bo.getReviewers(),
                bo.getGitFileCreationDateAndTimeInSubBranch(),
                bo.getFileMRApprovedDateAndTime(),
                bo.getFileProcessedDateAndTime(),
                bo.getFileCreationAndMergedIntoMainBranchDateAndTime(),
                bo.getFileModifiedInGitHubDateAndTime(),
                bo.getGitHubFileInToSubBranchPusherName(),
                bo.getFileGithubFileMergedIntoMainBranchMergerName(),
                bo.getFileGithubFileMergedIntoMainBranchMergerEmailId(),
                bo.getGitHubFileInToSubBranchPusherEmailId(),
                Days.valueOf(bo.getFileUploadedIntoGitHubSubBranchDay().getValue()),
                Days.valueOf(bo.getFileUploadedIntoGitHubMainBranchDay().getValue()),
                Answer.valueOf(bo.getIsFileUploadedToGitHubMainBranchInWorkingDay().getValue()),
                Answer.valueOf(bo.getIsFileUploadedToGitHubSubBranchInWorkingDay().getValue()),
                Answer.valueOf(bo.getIsFileModifiedInGitHub().getValue()),
                Answer.valueOf(bo.getIsFileDeletedInGitHub().getValue()),
                Answer.valueOf(bo.getIsFileProcessed().getValue()),
                ProcessingStatus.valueOf(bo.getFileprocessingStatus().getValue()),
                Days.valueOf(bo.getFileFirstProcessingDay().getValue()),
                Days.valueOf(bo.getFileLastProcessingDay().getValue()),
                Answer.valueOf(bo.getIsFileFirstProcessedInWorkingDay().getValue()),
                Answer.valueOf(bo.getIsFileLastProcessedInWorkingDay().getValue()),
                bo.getFileProcessingNumberOfCount(),
                bo.getFileProcessingError(),
                Answer.valueOf(bo.getIsFileCreatedInServer().getValue()),
                Answer.valueOf(bo.getIsFileDeletedInServer().getValue()),
                Answer.valueOf(bo.getIsFileModifiedInServer().getValue()),
                bo.isFileValidationStatus(),
                MarketEvents.valueOf(bo.getEventName().getEventName()),
                bo.getFirstClearingCode(),
                bo.getLastClearingCode(),
                bo.getFirstClearingMessage(),
                bo.getLastClearingMessage(),
                bo.getNumberOfRecords(),
                bo.getFileInformationPlaceOfCreation(),
                bo.getUserNamesOfModifyFileName(),
                bo.getCommitMessagesInSubBranch(),
                bo.getCommitMessagesInMainBranch(),
                bo.getDateAndTimeFileModifiedInServer(),
                RecordStatus.valueOf(bo.getGitHubFileStatus().getValue()),
                bo.getFileInformationRecordCreatedDateAndTime(),
                bo.getFileInformationRecordModifiedDateAndTime(),
                ProcessingStatus.valueOf(bo.getFileInformationStatus().getValue()),
                RecordStatus.valueOf(bo.getRecordStatusBO().getValue())
        );
    }

    public static FileInformationBO fileInformationToBO(FileInformation fi) {
        
        FileInformationBO bo = new FileInformationBO();
        bo.setFileInformationUuid(fi.fileInformationUuid());
        bo.setFileName(fi.fileName());
        bo.setFileType(fi.fileType());
        bo.setFileSize(fi.fileSize());
        bo.setFileUri(fi.fileUri());
        bo.setFileFolderName(fi.fileFolderName());
        bo.setFileStockDetailsUuid(fi.fileStockDetailsUuid());
        bo.setFileMRPublisherName(fi.fileMRPublisherName());
        bo.setFileMRApprovedName(fi.fileMRApprovedName());
        bo.setFileMRPublisherDateAndTime(fi.fileMRPublisherDateAndTime());
        bo.setAssigneesNames(fi.AssigneesNames());
        bo.setReviewers(fi.reviewers());
        bo.setGitFileCreationDateAndTimeInSubBranch(fi.gitFileCreationDateAndTimeInSubBranch());
        bo.setFileMRApprovedDateAndTime(fi.fileMRApprovedDateAndTime());
        bo.setFileProcessedDateAndTime(fi.fileProcessedDateAndTime());
        bo.setFileCreationAndMergedIntoMainBranchDateAndTime(fi.fileCreationAndMergedIntoMainBranchDateAndTime());
        bo.setFileModifiedInGitHubDateAndTime(fi.fileModifiedInGitHubDateAndTime());
        bo.setGitHubFileInToSubBranchPusherName(fi.gitHubFileInToSubBranchPusherName());
        bo.setFileGithubFileMergedIntoMainBranchMergerName(fi.fileGithubFileMergedIntoMainBranchMergerName());
        bo.setFileGithubFileMergedIntoMainBranchMergerEmailId(fi.fileGithubFileMergedIntoMainBranchMergerEmailId());
        bo.setGitHubFileInToSubBranchPusherEmailId(fi.gitHubFileInToSubBranchPusherEmailId());
        bo.setFileUploadedIntoGitHubSubBranchDay(DaysBO.valueOf(fi.fileUploadedIntoGitHubSubBranchDay().getValue()));
        bo.setFileUploadedIntoGitHubMainBranchDay(DaysBO.valueOf(fi.fileUploadedIntoGitHubMainBranchDay().getValue()));
        bo.setIsFileUploadedToGitHubMainBranchInWorkingDay(AnswerBO.valueOf(fi.isFileUploadedToGitHubMainBranchInWorkingDay().getValue()));
        bo.setIsFileUploadedToGitHubSubBranchInWorkingDay(AnswerBO.valueOf(fi.isFileUploadedToGitHubSubBranchInWorkingDay().getValue()));
        bo.setIsFileModifiedInGitHub(AnswerBO.valueOf(fi.isFileModifiedInGitHub().getValue()));
        bo.setIsFileDeletedInGitHub(AnswerBO.valueOf(fi.isFileDeletedInGitHub().getValue()));
        bo.setIsFileProcessed(AnswerBO.valueOf(fi.isFileProcessed().getValue()));
        bo.setFileprocessingStatus(ProcessingStatusBO.valueOf(fi.fileprocessingStatus().getValue()));
        bo.setFileFirstProcessingDay(DaysBO.valueOf(fi.fileFirstProcessingDay().getValue()));
        bo.setFileLastProcessingDay(DaysBO.valueOf(fi.fileLastProcessingDay().getValue()));
        bo.setIsFileFirstProcessedInWorkingDay(AnswerBO.valueOf(fi.isFileFirstProcessedInWorkingDay().getValue()));
        bo.setIsFileLastProcessedInWorkingDay(AnswerBO.valueOf(fi.isFileLastProcessedInWorkingDay().getValue()));
        bo.setFileProcessingNumberOfCount(fi.fileProcessingNumberOfCount());
        bo.setFileProcessingError(fi.fileProcessingError());
        bo.setIsFileCreatedInServer(AnswerBO.valueOf(fi.isFileCreatedInServer().getValue()));
        bo.setIsFileDeletedInServer(AnswerBO.valueOf(fi.isFileDeletedInServer().getValue()));
        bo.setIsFileModifiedInServer(AnswerBO.valueOf(fi.isFileModifiedInServer().getValue()));
        bo.setFileValidationStatus(fi.fileValidationStatus());
        bo.setEventName(MarketEventsBO.valueOf(fi.eventName().getEventName()));
        bo.setFirstClearingCode(fi.firstClearingCode());
        bo.setLastClearingCode(fi.lastClearingCode());
        bo.setFirstClearingMessage(fi.firstClearingMessage());
        bo.setLastClearingMessage(fi.lastClearingMessage());
        bo.setNumberOfRecords(fi.numberOfRecords());
        bo.setFileInformationPlaceOfCreation(fi.fileInformationPlaceOfCreation());
        bo.setUserNamesOfModifyFileName(fi.userNamesOfModifyFileName());
        bo.setCommitMessagesInSubBranch(fi.commitMessagesInSubBranch());
        bo.setCommitMessagesInMainBranch(fi.commitMessagesInMainBranch());
        bo.setDateAndTimeFileModifiedInServer(fi.dateAndTimeFileModifiedInServer());
        bo.setGitHubFileStatus(RecordStatusBO.valueOf(fi.gitHubFileStatus().getValue()));
        bo.setFileInformationRecordCreatedDateAndTime(fi.fileInformationRecordCreatedDateAndTime());
        bo.setFileInformationRecordModifiedDateAndTime(fi.fileInformationRecordModifiedDateAndTime());
        bo.setFileInformationStatus(ProcessingStatusBO.valueOf(fi.fileInformationStatus().getValue()));
        bo.setRecordStatusBO(RecordStatusBO.valueOf(fi.recordStatus().getValue()));
        return bo;
    }
}
