package funMarketFileInformation.service.dataFeeding;

import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.*;
import com.fsm.domins.information.operations.FunMarketFileInformationRetrievalMethods;
import funMarketClearing.clearingEngine.validation.CheckFileNamePattern;
import funMarketExceptions.FunMarketException;
import funMarketFileInformation.operations.FileInformationOperationsManagerBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

import static FunMarketUtils.Utils.*;

@Service(value = "buildNewFileInformation")
public class BuildNewFileInformation {

    private final static Logger log = LoggerFactory.getLogger(BuildNewFileInformation.class);
    private final Map<String, FileInformationOperationsManagerBO> fileInformationOperationsManagerBO;
    private final FunMarketFileInformationRetrievalMethods funMarketFileInformationRetrievalMethods;

    public BuildNewFileInformation(Map<String, FileInformationOperationsManagerBO> fileInformationOperationsManagerBO,
                                   @Qualifier(value = "funMarketFileInformationRetrievals") FunMarketFileInformationRetrievalMethods funMarketFileInformationRetrievalMethods) {
        this.fileInformationOperationsManagerBO = fileInformationOperationsManagerBO;
        this.funMarketFileInformationRetrievalMethods = funMarketFileInformationRetrievalMethods;
    }

    public FileInformationBO processNewFileInformation(Map<String, Object> gitMetadata) {
        try {
            if (Objects.isNull(gitMetadata) || gitMetadata.isEmpty()) {
                log.info("Metadata is null or empty, cannot process file information. Fail to save");
                throw new FunMarketException("Metadata cannot be null or empty");
            }
            final String fileName =getStringValue(gitMetadata, "fileName", UNKNOWN);
            FileInformationBO existingFileInformation = funMarketFileInformationRetrievalMethods.findByFileName(fileName);

            if(Objects.nonNull(existingFileInformation) && existingFileInformation.getFileInformationUuid().isBlank()) {
                log.info("File information already exists for file name: {}, cannot create new file information. Time: {}", fileName, currentTime());
                throw new FunMarketException("File information already exists for file name: " + fileName);
            }

            try{
                FileMetadataBO stockFileDetailsBO = new FileMetadataBO();
                stockFileDetailsBO.setFileName(fileName);
                new CheckFileNamePattern().process(stockFileDetailsBO);
            }catch (FunMarketException e){
                log.error("File name pattern validation failed for file name: {}. Error: {}", fileName, e.getMessage());
                throw new FunMarketException("File name pattern validation failed for file name: " + fileName + ". Error: " + e.getMessage());
            }
            String fileGithubFileMergedIntoMainBranchMergerName = getStringValue(gitMetadata, "fileGithubFileMergedIntoMainBranchMergerName", UNKNOWN);

            LocalDateTime creationDateAndTimeInSubBranch = parseUploadDateTime(gitMetadata, "gitFileCreationDateAndTimeInSubBranch");
            DayOfWeek dayOfWeekSubBranch = creationDateAndTimeInSubBranch.getDayOfWeek();
            List<LocalDateTime> creationDateAndTimeInSubBranchLocalDateTimes = new LinkedList<>();
            creationDateAndTimeInSubBranchLocalDateTimes.add(creationDateAndTimeInSubBranch);

            LocalDateTime mRApprovedDateAndTime = parseUploadDateTime(gitMetadata, "fileMRApprovedDateAndTime");
            List<LocalDateTime> fileMRApprovedDateAndTime = new LinkedList<>();
            fileMRApprovedDateAndTime.add(mRApprovedDateAndTime);

            LocalDateTime fileCreationAndMergedIntoMainBranchDateAndTime = parseUploadDateTime(gitMetadata, "fileCreationAndMergedIntoMainBranchDateAndTime");
            DayOfWeek dayOfWeekMainBranch = fileCreationAndMergedIntoMainBranchDateAndTime.getDayOfWeek();
            Map<String,LocalDateTime> mapFileCreationAndMergedIntoMainBranchDateAndTime = new LinkedHashMap<>();
            mapFileCreationAndMergedIntoMainBranchDateAndTime.put("MERGED BY: "+ fileGithubFileMergedIntoMainBranchMergerName, fileCreationAndMergedIntoMainBranchDateAndTime);

            LocalDateTime fileModifiedInGitHubDateAndTime = parseUploadDateTime(gitMetadata, "fileModifiedInGitHubDateAndTime");
            List<LocalDateTime> fileModifiedInGitHubDateAndTimeList = new LinkedList<>();
            fileModifiedInGitHubDateAndTimeList.add(fileModifiedInGitHubDateAndTime);

            final String fileMRPublisherName = getStringValue(gitMetadata, "fileMRPublisherName", UNKNOWN);
            final String fileMRApprovedName = getStringValue(gitMetadata, "fileMRApprovedName", UNKNOWN);

            Map<String, LocalDateTime> UserNamesOfModifyFileName = new LinkedHashMap<>();
            UserNamesOfModifyFileName.put("MODIFIED NAME: " + fileMRPublisherName, creationDateAndTimeInSubBranch);
            UserNamesOfModifyFileName.put("MODIFIED NAME: " + fileGithubFileMergedIntoMainBranchMergerName, mRApprovedDateAndTime);

            Map<String, LocalDateTime>  commitOnSubBranch = new LinkedHashMap<>();
            commitOnSubBranch.put(getStringValue(gitMetadata, "commitMessagesInSubBranch", "NO_COMMIT_MESSAGE"), creationDateAndTimeInSubBranch);

            Map<String, LocalDateTime> commitOnMainBranch = new LinkedHashMap<>();
            commitOnMainBranch.put(getStringValue(gitMetadata, "commitMessagesInMainBranch)", "NO_COMMIT_MESSAGE"), fileCreationAndMergedIntoMainBranchDateAndTime);

            FileInformationBO informationBO = new FileInformationBO();
            informationBO.setFileInformationUuid(UUID.randomUUID().toString());
            informationBO.setFileName(fileName);
            informationBO.setFileType(getStringValue(gitMetadata, "fileType", UNKNOWN));
            informationBO.setFileSize(getLongValue(gitMetadata, "fileSize", 0L));
            informationBO.setFileUri(NOT_PROCESSED);
            informationBO.setFileFolderName(NOT_PROCESSED);
            informationBO.setFileStockDetailsUuid(NOT_PROCESSED);
            informationBO.setFileMRPublisherName(fileMRPublisherName);
            informationBO.setFileMRApprovedName(fileMRApprovedName);
            informationBO.setFileMRPublisherDateAndTime(parseUploadDateTime(gitMetadata, "fileMRPublisherDateAndTime"));
            informationBO.setAssigneesNames(getStringValue(gitMetadata, "AssigneesNames", UNKNOWN));
            informationBO.setReviewers(getStringValue(gitMetadata, "reviewers", UNKNOWN));
            informationBO.setGitFileCreationDateAndTimeInSubBranch(creationDateAndTimeInSubBranchLocalDateTimes);
            informationBO.setFileMRApprovedDateAndTime(fileMRApprovedDateAndTime);
            informationBO.setFileProcessedDateAndTime(new LinkedList<>());
            informationBO.setFileCreationAndMergedIntoMainBranchDateAndTime(mapFileCreationAndMergedIntoMainBranchDateAndTime);
            informationBO.setFileModifiedInGitHubDateAndTime(fileModifiedInGitHubDateAndTimeList);
            informationBO.setGitHubFileInToSubBranchPusherName(getStringValue(gitMetadata, "gitHubFileInToSubBranchPusherName", UNKNOWN));
            informationBO.setFileGithubFileMergedIntoMainBranchMergerName(getStringValue(gitMetadata, "fileGithubFileMergedIntoMainBranchMergerName", UNKNOWN));
            informationBO.setFileGithubFileMergedIntoMainBranchMergerEmailId(getStringValue(gitMetadata, "fileGithubFileMergedIntoMainBranchMergerEmailId", UNKNOWN));
            informationBO.setGitHubFileInToSubBranchPusherEmailId(getStringValue(gitMetadata, "gitHubFileInToSubBranchPusherEmailId", UNKNOWN));
            informationBO.setFileUploadedIntoGitHubSubBranchDay(convertDayOfWeekToDaysEnum(dayOfWeekSubBranch));
            informationBO.setFileUploadedIntoGitHubMainBranchDay(convertDayOfWeekToDaysEnum(dayOfWeekMainBranch));
            informationBO.setIsFileUploadedToGitHubMainBranchInWorkingDay(isWorkingDay(dayOfWeekMainBranch));
            informationBO.setIsFileUploadedToGitHubSubBranchInWorkingDay(isWorkingDay(dayOfWeekSubBranch));
            informationBO.setIsFileModifiedInGitHub(AnswerBO.N);
            informationBO.setIsFileDeletedInGitHub(AnswerBO.N);
            informationBO.setIsFileProcessed(AnswerBO.N);
            informationBO.setFileprocessingStatus(ProcessingStatusBO.NOT_PROCESSED);
            informationBO.setFileFirstProcessingDay(DaysBO.UNKNOWN);
            informationBO.setFileLastProcessingDay(DaysBO.UNKNOWN);
            informationBO.setIsFileFirstProcessedInWorkingDay(AnswerBO.N);
            informationBO.setIsFileLastProcessedInWorkingDay(AnswerBO.N);
            informationBO.setFileProcessingNumberOfCount(0);
            informationBO.setFileProcessingError(UNKNOWN);
            informationBO.setIsFileCreatedInServer(AnswerBO.N);
            informationBO.setIsFileDeletedInServer(AnswerBO.N);
            informationBO.setIsFileModifiedInServer(AnswerBO.N);
            informationBO.setFileValidationStatus(false);
            informationBO.setEventName(MarketEventsBO.DAILY);
            informationBO.setFirstClearingCode(UNKNOWN);
            informationBO.setLastClearingCode(UNKNOWN);
            informationBO.setFirstClearingMessage(UNKNOWN);
            informationBO.setLastClearingMessage(UNKNOWN);
            informationBO.setNumberOfRecords(0);
            informationBO.setFileInformationPlaceOfCreation(BuildNewFileInformation.class.getSimpleName());
            informationBO.setUserNamesOfModifyFileName(UserNamesOfModifyFileName);
            informationBO.setCommitMessagesInSubBranch(commitOnSubBranch);
            informationBO.setCommitMessagesInMainBranch(commitOnMainBranch);
            informationBO.setDateAndTimeFileModifiedInServer(new LinkedList<>());
            informationBO.setGitHubFileStatus(RecordStatusBO.ADDED);
            informationBO.setFileInformationRecordCreatedDateAndTime(currentTime());
            informationBO.setFileInformationRecordModifiedDateAndTime(new LinkedHashMap<>());
            informationBO.setFileInformationStatus(ProcessingStatusBO.NOT_PROCESSED);
            informationBO.setRecordStatusBO(RecordStatusBO.ADDED);

            return saveFileInformation(informationBO);

        } catch (Exception e) {
            log.error("Error building new FileInformation object from GitHub metadata", e);
            throw new FunMarketException("Failed to build new FileInformation object: " + e.getMessage());
        }
    }

    public FileInformationBO saveFileInformation(FileInformationBO fileInformationBO) {
        if (Objects.isNull(fileInformationBO)) {
            log.info("FileInformationBO is null, cannot save file information.");
            throw new FunMarketException("FileInformationBO cannot be null");
        }
        try {
            FileInformationOperationsManagerBO operationsManager = fileInformationOperationsManagerBO.getOrDefault("saveFileInformation", null);
            if (Objects.isNull(operationsManager)) {
                throw new FunMarketException("SaveFileInformation operations manager not found");
            }
            return operationsManager.saveFileInformationMetaData(fileInformationBO);
        } catch (FunMarketException e) {
            log.error("Error occurred while saving file information: {}", e.getMessage());
            throw new FunMarketException("Error occurred while saving file information: " + e.getMessage());
        }
    }
}

