package funMarketFileInformation.service.dataFeeding;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domainsMapping.constantsBO.*;
import com.fsm.domins.information.operations.FunMarketFileInformationRetrievalMethods;
import funMarketClearing.Operations.FunMarketFileClearing;
import funMarketClearing.constants.FileValidationStatus;
import funMarketExceptions.FunMarketException;
import funMarketFileInformation.operations.FileInformationOperationsManagerBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

import static FunMarketUtils.Utils.*;

@Service(value = "handleModifiedFile")
public class HandleModifiedFile {

    private static final Logger log = LoggerFactory.getLogger(HandleModifiedFile.class);

    // Metadata Key Constants
    private static final String FILE_NAME = "fileName";
    private static final String FILE_TYPE = "fileType";
    private static final String FILE_SIZE = "fileSize";
    private static final String FILE_MR_PUBLISHER_NAME = "fileMRPublisherName";
    private static final String FILE_MR_APPROVED_NAME = "fileMRApprovedName";
    private static final String FILE_MR_PUBLISHER_DATE_TIME = "fileMRPublisherDateAndTime";
    private static final String FILE_GITHUB_MERGER_NAME = "fileGithubFileMergedIntoMainBranchMergerName";
    private static final String FILE_GITHUB_MERGER_EMAIL = "fileGithubFileMergedIntoMainBranchMergerEmailId";
    private static final String GIT_CREATION_DATETIME = "gitFileCreationDateAndTimeInSubBranch";
    private static final String MR_APPROVED_DATETIME = "fileMRApprovedDateAndTime";
    private static final String FILE_MERGED_DATETIME = "fileCreationAndMergedIntoMainBranchDateAndTime";
    private static final String FILE_MODIFIED_GITHUB_DATETIME = "fileModifiedInGitHubDateAndTime";
    private static final String GIT_PUSHER_NAME = "gitHubFileInToSubBranchPusherName";
    private static final String GIT_PUSHER_EMAIL = "gitHubFileInToSubBranchPusherEmailId";
    private static final String ASSIGNEES = "AssigneesNames";
    private static final String REVIEWERS = "reviewers";
    private static final String COMMIT_MSG_SUB_BRANCH = "commitMessagesInSubBranch";
    private static final String COMMIT_MSG_MAIN_BRANCH = "commitMessagesInMainBranch)";
    private static final String NO_COMMIT_MESSAGE = "NO_COMMIT_MESSAGE";
    private static final String MERGED_BY_PREFIX = "MERGED BY: ";
    private static final String MODIFIED_NAME_PREFIX = "MODIFIED NAME: ";

    private final Map<String, FileInformationOperationsManagerBO> fileInformationOperationsManagerBO;
    private final FunMarketFileInformationRetrievalMethods funMarketFileInformationRetrievalMethods;
    private final FunMarketFileClearing funMarketFileClearing;

    public HandleModifiedFile(Map<String, FileInformationOperationsManagerBO> fileInformationOperationsManagerBO,
                              @Qualifier(value = "funMarketFileInformationRetrievals") 
                              FunMarketFileInformationRetrievalMethods funMarketFileInformationRetrievalMethods,
                              @Qualifier(value = "funMarketFileClearing") 
                              FunMarketFileClearing funMarketFileClearing) {
        this.fileInformationOperationsManagerBO = fileInformationOperationsManagerBO;
        this.funMarketFileInformationRetrievalMethods = funMarketFileInformationRetrievalMethods;
        this.funMarketFileClearing = funMarketFileClearing;
    }

    @Transactional
    public FileInformationBO processModificationFileInformation(Map<String, Object> gitMetadata) {
        String fileName = validateAndRetrieveFileInformation(gitMetadata);
        FileInformationBO fileInfo = funMarketFileInformationRetrievalMethods.findByFileName(fileName);

        log.info("Processing modification for file: {}. Time: {}", fileName, currentTime());

        // Parse metadata once
        GitFileMetadata metadata = parseGitMetadata(gitMetadata);

        // Populate file information
        populateCommonFileInformation(fileInfo, metadata);
        setModificationStatus(fileInfo);

        // Modify and persist
        FileInformationBO modifiedFileInfo = modifyFileInformation(fileInfo);

        if (modifiedFileInfo.getFileInformationUuid() != null) {
            log.info("File modification completed successfully for: {}. Time: {}", fileName, currentTime());
            return processClearingForModifiedFile(fileName, modifiedFileInfo);
        } else {
            log.error("Failed to modify file: {}. No UUID returned.", fileName);
            throw new FunMarketException("Failed to modify file information for file name: " + fileName);
        }
    }

    public FileInformationBO handleRemovedFile(Map<String, Object> gitMetadata) {
        String fileName = validateAndRetrieveFileInformation(gitMetadata);
        FileInformationBO fileInfo = funMarketFileInformationRetrievalMethods.findByFileName(fileName);

        log.info("Processing removal for file: {}. Time: {}", fileName, currentTime());

        // Parse metadata once
        GitFileMetadata metadata = parseGitMetadata(gitMetadata);

        // Populate file information
        populateCommonFileInformation(fileInfo, metadata);
        setRemovalStatus(fileInfo);

        log.info("File removal processing completed for: {}", fileName);
        return modifyFileInformation(fileInfo);
    }


    /**
     * Validates and retrieves existing file information from metadata
     */
    private String validateAndRetrieveFileInformation(Map<String, Object> gitMetadata) {
        if (Objects.isNull(gitMetadata) || gitMetadata.isEmpty()) {
            log.error("Metadata is null or empty");
            throw new FunMarketException("Metadata cannot be null or empty");
        }

        String fileName = getStringValue(gitMetadata, FILE_NAME, null);
        if (Objects.isNull(fileName)) {
            log.error("File name is missing in metadata");
            throw new FunMarketException("File name is missing in metadata");
        }

        FileInformationBO existingFileInfo = funMarketFileInformationRetrievalMethods.findByFileName(fileName);
        if (Objects.isNull(existingFileInfo)) {
            log.error("No existing file information found for: {}", fileName);
            throw new FunMarketException("No existing file information found for file name: " + fileName);
        }

        return fileName;
    }

    /**
     * Parses all metadata from git into a structured object
     */
    private GitFileMetadata parseGitMetadata(Map<String, Object> gitMetadata) {
        GitFileMetadata metadata = new GitFileMetadata();

        metadata.fileType = getStringValue(gitMetadata, FILE_TYPE, UNKNOWN);
        metadata.fileSize = getLongValue(gitMetadata, FILE_SIZE, 0L);
        metadata.fileMRPublisherName = getStringValue(gitMetadata, FILE_MR_PUBLISHER_NAME, UNKNOWN);
        metadata.fileMRApprovedName = getStringValue(gitMetadata, FILE_MR_APPROVED_NAME, UNKNOWN);
        metadata.fileGithubMergerName = getStringValue(gitMetadata, FILE_GITHUB_MERGER_NAME, UNKNOWN);
        metadata.fileGithubMergerEmail = getStringValue(gitMetadata, FILE_GITHUB_MERGER_EMAIL, UNKNOWN);
        metadata.gitPusherName = getStringValue(gitMetadata, GIT_PUSHER_NAME, UNKNOWN);
        metadata.gitPusherEmail = getStringValue(gitMetadata, GIT_PUSHER_EMAIL, UNKNOWN);
        metadata.assigneesNames = getStringValue(gitMetadata, ASSIGNEES, UNKNOWN);
        metadata.reviewers = getStringValue(gitMetadata, REVIEWERS, UNKNOWN);

        metadata.fileMRPublisherDateTime = parseUploadDateTime(gitMetadata, FILE_MR_PUBLISHER_DATE_TIME);
        metadata.creationDateTimeSubBranch = parseUploadDateTime(gitMetadata, GIT_CREATION_DATETIME);
        metadata.mRApprovedDateTime = parseUploadDateTime(gitMetadata, MR_APPROVED_DATETIME);
        metadata.fileCreationMergedDateTime = parseUploadDateTime(gitMetadata, FILE_MERGED_DATETIME);
        metadata.fileModifiedGitHubDateTime = parseUploadDateTime(gitMetadata, FILE_MODIFIED_GITHUB_DATETIME);

        metadata.commitMsgSubBranch = getStringValue(gitMetadata, COMMIT_MSG_SUB_BRANCH, NO_COMMIT_MESSAGE);
        metadata.commitMsgMainBranch = getStringValue(gitMetadata, COMMIT_MSG_MAIN_BRANCH, NO_COMMIT_MESSAGE);

        metadata.dayOfWeekSubBranch = metadata.creationDateTimeSubBranch.getDayOfWeek();
        metadata.dayOfWeekMainBranch = metadata.fileCreationMergedDateTime.getDayOfWeek();

        return metadata;
    }

    /**
     * Populates common file information for both modification and removal
     */
    private void populateCommonFileInformation(FileInformationBO fileInfo, GitFileMetadata metadata) {
        // Basic file information
        fileInfo.setFileType(metadata.fileType);
        fileInfo.setFileSize(metadata.fileSize);
        fileInfo.setFileUri(NOT_PROCESSED);
        fileInfo.setFileFolderName(NOT_PROCESSED);
        fileInfo.setFileStockDetailsUuid(NOT_PROCESSED);

        // MR and approval information
        fileInfo.setFileMRPublisherName(metadata.fileMRPublisherName);
        fileInfo.setFileMRApprovedName(metadata.fileMRApprovedName);
        fileInfo.setFileMRPublisherDateAndTime(metadata.fileMRPublisherDateTime);
        fileInfo.setAssigneesNames(metadata.assigneesNames);
        fileInfo.setReviewers(metadata.reviewers);

        // Git user information
        fileInfo.setGitHubFileInToSubBranchPusherName(metadata.gitPusherName);
        fileInfo.setFileGithubFileMergedIntoMainBranchMergerName(metadata.fileGithubMergerName);
        fileInfo.setFileGithubFileMergedIntoMainBranchMergerEmailId(metadata.fileGithubMergerEmail);
        fileInfo.setGitHubFileInToSubBranchPusherEmailId(metadata.gitPusherEmail);

        // Date and time collections
        fileInfo.getGitFileCreationDateAndTimeInSubBranch().add(metadata.creationDateTimeSubBranch);
        fileInfo.getFileMRApprovedDateAndTime().add(metadata.mRApprovedDateTime);
        fileInfo.getFileCreationAndMergedIntoMainBranchDateAndTime().put(
                MERGED_BY_PREFIX + metadata.fileGithubMergerName,
                metadata.fileCreationMergedDateTime
        );
        fileInfo.getFileModifiedInGitHubDateAndTime().add(metadata.fileModifiedGitHubDateTime);

        // Day of week information
        fileInfo.setFileUploadedIntoGitHubSubBranchDay(convertDayOfWeekToDaysEnum(metadata.dayOfWeekSubBranch));
        fileInfo.setFileUploadedIntoGitHubMainBranchDay(convertDayOfWeekToDaysEnum(metadata.dayOfWeekMainBranch));
        fileInfo.setIsFileUploadedToGitHubMainBranchInWorkingDay(isWorkingDay(metadata.dayOfWeekMainBranch));
        fileInfo.setIsFileUploadedToGitHubSubBranchInWorkingDay(isWorkingDay(metadata.dayOfWeekSubBranch));

        // Common flags
        fileInfo.setIsFileModifiedInGitHub(AnswerBO.Y);
        fileInfo.setIsFileDeletedInGitHub(AnswerBO.N);
        fileInfo.setIsFileProcessed(AnswerBO.N);
        fileInfo.setFileInformationPlaceOfCreation(HandleModifiedFile.class.getSimpleName());

        // User modification names
        Map<String, LocalDateTime> userModifications = new LinkedHashMap<>();
        userModifications.put(MODIFIED_NAME_PREFIX + metadata.fileMRPublisherName, metadata.creationDateTimeSubBranch);
        userModifications.put(MODIFIED_NAME_PREFIX + metadata.fileGithubMergerName, metadata.mRApprovedDateTime);
        fileInfo.getUserNamesOfModifyFileName().putAll(userModifications);

        // Commit messages
        fileInfo.getCommitMessagesInSubBranch().put(metadata.commitMsgSubBranch, metadata.creationDateTimeSubBranch);
        fileInfo.getCommitMessagesInMainBranch().put(metadata.commitMsgMainBranch, metadata.fileCreationMergedDateTime);
    }

    /**
     * Sets modification-specific status
     */
    private void setModificationStatus(FileInformationBO fileInfo) {
        fileInfo.setFileprocessingStatus(ProcessingStatusBO.MODIFICATION_PROGRESS);
        fileInfo.setGitHubFileStatus(RecordStatusBO.MODIFIED);
        fileInfo.getFileInformationRecordModifiedDateAndTime().put(RecordStatusBO.MODIFIED.getValue(), currentTime());
        fileInfo.setFileInformationStatus(ProcessingStatusBO.MODIFICATION_PROGRESS);
    }

    /**
     * Sets removal-specific status
     */
    private void setRemovalStatus(FileInformationBO fileInfo) {
        fileInfo.setFileprocessingStatus(ProcessingStatusBO.REMOVING_IN_PROCESS);
        fileInfo.setGitHubFileStatus(RecordStatusBO.REMOVED);
        fileInfo.getFileInformationRecordModifiedDateAndTime().put(RecordStatusBO.MODIFIED.getValue(), currentTime());
        fileInfo.setFileInformationStatus(ProcessingStatusBO.SUSPENDED);
    }

    /**
     * Processes clearing for modified files
     */
    private FileInformationBO processClearingForModifiedFile(String fileName, FileInformationBO fileInfo) {
        log.info("Creating clearing record for modified file: {}. Time: {}", fileName, currentTime());

        FileClearingBO fileClearingBO = funMarketFileClearing.saveClearing(
                fileInfo.getFileStockDetailsUuid(),
                fileInfo.getFileName(),
                FileValidationStatus.NOT_CLEARED.getStatus(),
                ErrorCodesBO.ERR_0002.getCode(),
                ErrorCodesBO.ERR_0002.getMessage(),
                "FileInformationEng: FileDate Modified GitHub event processing",
                RecordStatusBO.FILE_DATA_MODIFIED
        );

        if (fileClearingBO.getFileClearingUuid() != null) {
            log.info("Clearing record created successfully for: {}. Time: {}", fileName, currentTime());
            return fileInfo;
        } else {
            log.error("Failed to create clearing record for: {}", fileName);
            throw new FunMarketException("Failed to create clearing record for modified file: " + fileName);
        }
    }

    /**
     * Modifies and persists file information
     */
    private FileInformationBO modifyFileInformation(FileInformationBO fileInfo) {
        if (Objects.isNull(fileInfo)) {
            log.error("FileInformationBO is null");
            throw new FunMarketException("FileInformationBO cannot be null");
        }

        try {
            FileInformationOperationsManagerBO operationsManager = 
                    fileInformationOperationsManagerBO.get("funMarketModifyFileInformation");
            
            if (Objects.isNull(operationsManager)) {
                log.error("File information operations manager not found");
                throw new FunMarketException("File information operations manager not found");
            }

            return operationsManager.saveFileInformationMetaData(fileInfo);
        } catch (FunMarketException e) {
            log.error("Error occurred while modifying file information: {}", e.getMessage());
            throw new FunMarketException("Error occurred while modifying file information: " + e.getMessage());
        }
    }

    /**
     * Inner class to hold parsed git metadata - improves readability and reduces parameter passing
     */
    private static class GitFileMetadata {
        String fileType;
        long fileSize;
        String fileMRPublisherName;
        String fileMRApprovedName;
        String fileGithubMergerName;
        String fileGithubMergerEmail;
        String gitPusherName;
        String gitPusherEmail;
        String assigneesNames;
        String reviewers;

        LocalDateTime fileMRPublisherDateTime;
        LocalDateTime creationDateTimeSubBranch;
        LocalDateTime mRApprovedDateTime;
        LocalDateTime fileCreationMergedDateTime;
        LocalDateTime fileModifiedGitHubDateTime;

        String commitMsgSubBranch;
        String commitMsgMainBranch;

        DayOfWeek dayOfWeekSubBranch;
        DayOfWeek dayOfWeekMainBranch;
    }
}
