package funMarketFileInformation.service.dataFeeding;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.AnswerBO;
import com.fsm.domainsMapping.constantsBO.DaysBO;
import com.fsm.domainsMapping.constantsBO.ProcessingStatusBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import funMarketClearing.constants.FileValidationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static FunMarketUtils.Utils.*;

@Service(value = "processedFileInformation")
public class ProcessedFileInformation {

    private final static Logger log = LoggerFactory.getLogger(ProcessedFileInformation.class);


    public FileInformationBO handleProcessedFileInformationNotFound(FileMetadataBO stockFileDetails, FileClearingBO fileClearing) {
        log.info("Creating new FileInformation for file: {}", stockFileDetails.getFileName());

        LocalDateTime time = stockFileDetails.getFileUploadDate();

        List<LocalDateTime> localDateTimes = new LinkedList<>();
        localDateTimes.add(time);

        Map<String, LocalDateTime> stringLocalDateTimeMap = new LinkedHashMap<>();
        stringLocalDateTimeMap.put(UNKNOWN, time);

        DaysBO day = convertDayOfWeekToDaysEnum(time.getDayOfWeek());
        AnswerBO workingDayStatus = isWorkingDay(time.getDayOfWeek());

        Map<String, LocalDateTime> commits = new LinkedHashMap<>();
        commits.put(UNKNOWN, time);

        FileInformationBO informationBO = new FileInformationBO();

        informationBO.setFileInformationUuid(UUID.randomUUID().toString());
        informationBO.setFileName(stockFileDetails.getFileName());
        informationBO.setFileType(stockFileDetails.getFileType());
        informationBO.setFileSize(stockFileDetails.getFileSize());
        informationBO.setFileUri(stockFileDetails.getUri());
        informationBO.setFileFolderName(stockFileDetails.getFolderName());
        informationBO.setFileStockDetailsUuid(stockFileDetails.getFileUUID());
        informationBO.setFileMRPublisherName(UNKNOWN);
        informationBO.setFileMRApprovedName(UNKNOWN);
        informationBO.setFileMRPublisherDateAndTime(time);
        informationBO.setAssigneesNames(UNKNOWN);
        informationBO.setReviewers(UNKNOWN);
        informationBO.setGitFileCreationDateAndTimeInSubBranch(localDateTimes);
        informationBO.setFileMRApprovedDateAndTime(localDateTimes);
        informationBO.setFileProcessedDateAndTime(localDateTimes);
        informationBO.setFileCreationAndMergedIntoMainBranchDateAndTime(stringLocalDateTimeMap);
        informationBO.setFileModifiedInGitHubDateAndTime(localDateTimes);
        informationBO.setGitHubFileInToSubBranchPusherName(UNKNOWN);
        informationBO.setFileGithubFileMergedIntoMainBranchMergerName(UNKNOWN);
        informationBO.setFileGithubFileMergedIntoMainBranchMergerEmailId(UNKNOWN);
        informationBO.setGitHubFileInToSubBranchPusherEmailId(UNKNOWN);
        informationBO.setFileUploadedIntoGitHubSubBranchDay(day);
        informationBO.setFileUploadedIntoGitHubMainBranchDay(day);
        informationBO.setIsFileUploadedToGitHubMainBranchInWorkingDay(workingDayStatus);
        informationBO.setIsFileUploadedToGitHubSubBranchInWorkingDay(workingDayStatus);
        informationBO.setIsFileModifiedInGitHub(AnswerBO.N);
        informationBO.setIsFileDeletedInGitHub(AnswerBO.N);
        informationBO.setIsFileProcessed(AnswerBO.Y);
        informationBO.setFileprocessingStatus(ProcessingStatusBO.PROCESSED);
        informationBO.setFileFirstProcessingDay(day);
        informationBO.setFileLastProcessingDay(day);
        informationBO.setIsFileFirstProcessedInWorkingDay(workingDayStatus);
        informationBO.setIsFileLastProcessedInWorkingDay(workingDayStatus);
        informationBO.setFileProcessingNumberOfCount(1);
        informationBO.setFileProcessingError(fileClearing.getClearingMessage());
        informationBO.setIsFileCreatedInServer(AnswerBO.Y);
        informationBO.setIsFileDeletedInServer(AnswerBO.N);
        informationBO.setIsFileModifiedInServer(AnswerBO.N);
        informationBO.setFileValidationStatus(isClearingPassed(fileClearing));
        informationBO.setEventName(stockFileDetails.getEventNameBO());
        informationBO.setFirstClearingCode(fileClearing.getClearingCode());
        informationBO.setLastClearingCode(" ");
        informationBO.setFirstClearingMessage(fileClearing.getClearingMessage());
        informationBO.setLastClearingMessage(" ");
        informationBO.setNumberOfRecords(stockFileDetails.getFileData().size());
        informationBO.setFileInformationPlaceOfCreation("handleProcessedFileInformationNotFound: created new records in the server");
        informationBO.setUserNamesOfModifyFileName(stringLocalDateTimeMap);
        informationBO.setCommitMessagesInSubBranch(commits);
        informationBO.setCommitMessagesInMainBranch(commits);
        informationBO.setDateAndTimeFileModifiedInServer(new LinkedList<>());
        informationBO.setGitHubFileStatus(RecordStatusBO.UNKNOWN);
        informationBO.setFileInformationRecordCreatedDateAndTime(currentTime());
        informationBO.setFileInformationRecordModifiedDateAndTime(new LinkedHashMap<>());
        informationBO.setFileInformationStatus(ProcessingStatusBO.PROCESSED);
        informationBO.setRecordStatusBO(RecordStatusBO.ADDED);

        return informationBO;
    }

    public FileInformationBO updateExistingFileInformation(FileInformationBO informationBO, FileMetadataBO stockFileDetails, FileClearingBO fileClearing) {
        log.info("Updating existing FileInformation for file: {}", stockFileDetails.getFileName());

        if (stockFileDetails.getFileUUID() != null &&
                (stockFileDetails.getRecordStatusBO().equals(RecordStatusBO.MODIFIED)
                        || stockFileDetails.getRecordStatusBO().equals(RecordStatusBO.ADDED))) {

            LocalDateTime fileUploadDate = stockFileDetails.getFileUploadDate();

            DaysBO day = convertDayOfWeekToDaysEnum(fileUploadDate.getDayOfWeek());

            AnswerBO workingDayStatus = isWorkingDay(fileUploadDate.getDayOfWeek());
            informationBO.setFileUri(stockFileDetails.getUri());
            informationBO.setFileFolderName(stockFileDetails.getFolderName());
            informationBO.setFileStockDetailsUuid(stockFileDetails.getFileUUID());
            informationBO.getFileProcessedDateAndTime().add(stockFileDetails.getFileUploadDate());
            informationBO.setIsFileProcessed(AnswerBO.Y);
            informationBO.setFileprocessingStatus(ProcessingStatusBO.PROCESSED);
            informationBO.setFileProcessingError(fileClearing.getClearingMessage());
            informationBO.setIsFileCreatedInServer(AnswerBO.Y);
            informationBO.setIsFileDeletedInServer(AnswerBO.N);
            informationBO.setIsFileModifiedInServer(AnswerBO.N);
            informationBO.setFileValidationStatus(isClearingPassed(fileClearing));
            informationBO.setEventName(stockFileDetails.getEventNameBO());
            informationBO.setNumberOfRecords(stockFileDetails.getFileData().size());
            informationBO.setFileInformationPlaceOfCreation("updateExistingFileInformation: created new records in the server");

            if (informationBO.getFileProcessingNumberOfCount() > 0) {
                log.info("File: {} has been processed {} times. Latest processing time: {}", stockFileDetails.getFileName(), informationBO.getFileProcessingNumberOfCount(), fileUploadDate);
                informationBO.setFileFirstProcessingDay(informationBO.getFileLastProcessingDay());
                informationBO.setFileLastProcessingDay(day);
                informationBO.setFileProcessingNumberOfCount(informationBO.getFileProcessingNumberOfCount() + 1);
                informationBO.setFirstClearingCode(informationBO.getLastClearingCode());
                informationBO.setLastClearingCode(fileClearing.getClearingCode());
                informationBO.setFirstClearingMessage(informationBO.getLastClearingMessage());
                informationBO.setLastClearingMessage(fileClearing.getClearingMessage());
                informationBO.setIsFileFirstProcessedInWorkingDay(informationBO.getIsFileLastProcessedInWorkingDay());
                informationBO.setIsFileLastProcessedInWorkingDay(workingDayStatus);

            } else {
                log.info("File: {} is being processed for the first time. Processing time: {}", stockFileDetails.getFileName(), fileUploadDate);
                informationBO.setFileFirstProcessingDay(day);
                informationBO.setFileLastProcessingDay(day);
                informationBO.setFileProcessingNumberOfCount(1);
                informationBO.setFirstClearingCode(fileClearing.getClearingCode());
                informationBO.setLastClearingCode(fileClearing.getClearingCode());
                informationBO.setFirstClearingMessage(fileClearing.getClearingMessage());
                informationBO.setLastClearingMessage(fileClearing.getClearingMessage());
                informationBO.setIsFileFirstProcessedInWorkingDay(workingDayStatus);
                informationBO.setIsFileLastProcessedInWorkingDay(workingDayStatus);
            }
            informationBO.getDateAndTimeFileModifiedInServer().add(stockFileDetails.getFileUploadDate());
            informationBO.getFileInformationRecordModifiedDateAndTime().put("FUN_MARKET_SYSTEM", fileUploadDate);
            informationBO.setFileInformationStatus(ProcessingStatusBO.PROCESSED);
            informationBO.setRecordStatusBO(RecordStatusBO.MODIFIED);
        }
        return informationBO;
    }

    private boolean isClearingPassed(FileClearingBO fileClearing) {
        return fileClearing != null
                && !fileClearing.getFileUuid().isBlank()
                && fileClearing.getFileValidationStatus().equals(FileValidationStatus.CLEARED.getStatus());
    }
}
