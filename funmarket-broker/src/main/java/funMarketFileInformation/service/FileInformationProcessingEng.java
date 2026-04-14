package funMarketFileInformation.service;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.AnswerBO;
import com.fsm.domins.information.operations.FunMarketFileInformationRetrievalMethods;
import funMarketExceptions.FunMarketException;
import funMarketFileInformation.service.dataFeeding.BuildNewFileInformation;
import funMarketFileInformation.service.dataFeeding.HandleModifiedFile;
import funMarketFileInformation.service.dataFeeding.ProcessedFileInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static FunMarketUtils.Utils.*;

@Service(value = "fileInformationProcessingEng")
public class FileInformationProcessingEng {

    private final static Logger log = LoggerFactory.getLogger(FileInformationProcessingEng.class);

    private final BuildNewFileInformation buildNewFileInformation;
    private final HandleModifiedFile handleModifiedFile;
    private final FunMarketFileInformationRetrievalMethods funMarketFileInformationRetrievalMethods;
    private final ProcessedFileInformation processedFileInformation;


    public FileInformationProcessingEng(@Qualifier(value = "buildNewFileInformation") BuildNewFileInformation buildNewFileInformation,
                                        @Qualifier(value = "handleModifiedFile") HandleModifiedFile handleModifiedFile,
                                        @Qualifier(value = "funMarketFileInformationRetrievals")FunMarketFileInformationRetrievalMethods funMarketFileInformationRetrievalMethods,
                                        @Qualifier(value = "processedFileInformation") ProcessedFileInformation processedFileInformation) {
        this.handleModifiedFile = handleModifiedFile;
        this.buildNewFileInformation = buildNewFileInformation;
        this.funMarketFileInformationRetrievalMethods = funMarketFileInformationRetrievalMethods;
        this.processedFileInformation = processedFileInformation;
    }

    public List<FileInformationBO> preProcessFileInformationMetaData(List<Map<String, Object>> gitMetaData) {
        if (Objects.isNull(gitMetaData) || gitMetaData.isEmpty()) {
            log.info("Metadata list is null or empty, cannot preprocess file information.");
            throw new FunMarketException("Metadata list cannot be null or empty");
        }
        log.info("Starting preprocessing of file information metadata. Total metadata entries to [ process: {}, time: {} ]", gitMetaData.size(), currentTime());
        List<FileInformationBO> fileInformationBOList = new LinkedList<>();
        gitMetaData.forEach(metaData -> {
            try {
                Map<String, Object> modifiedMetaData = removeDotsFromMapKeys(metaData);
                FileInformationBO fileInformationBO = processFileInformationEng(modifiedMetaData);
                fileInformationBOList.add(fileInformationBO);
            } catch (FunMarketException e) {
                log.info("Error occurred while preprocessing file information: {}", e.getMessage());
            }
        });
        if (fileInformationBOList.isEmpty()) {
            log.info("No valid file information found after preprocessing [ time: {} ].", currentTime());
            throw new FunMarketException("No valid file information found after preprocessing");
        } else {
            log.info("Preprocessing completed successfully. Total valid file information [ processed: {}, Time {} ]", fileInformationBOList.size(), currentTime());
            return fileInformationBOList;
        }

    }

    public FileInformationBO processFileInformationEng(Map<String, Object> gitMetaData) {
        if (Objects.isNull(gitMetaData) || gitMetaData.isEmpty()) {
            log.info("Metadata is null or empty, cannot process file information.");
            throw new FunMarketException("Metadata cannot be null or empty");
        }
        String fileName = getStringValue(gitMetaData, "fileName", "No File Name");
        if (fileName == null || fileName.isBlank()) {
            log.info("File name is null or blank, cannot process file information. time: [ {} ] ", currentTime());
            throw new FunMarketException("File name cannot be null or blank");
        }

        LocalDateTime uploadDateTime = parseUploadDateTime(gitMetaData, "uploadTime");
        DayOfWeek dayOfWeek = uploadDateTime.getDayOfWeek();
        AnswerBO isWorkingDay = isWorkingDay(dayOfWeek);
        String statusOfTheCommitedFile = getStringValue(gitMetaData, "status", "No Status").toLowerCase();

        if (log.isDebugEnabled()) {
            log.debug("Parsed GitHub metadata - [ File Name: {}, Git Status: {}, Upload Time: {}, Day of Week: {}, Is Working Day: {} ]", fileName, statusOfTheCommitedFile, uploadDateTime, dayOfWeek, isWorkingDay);
        }

        log.info("Starting processing of file information for [ File: {}  time: {} ]", fileName, currentTime());

        return switch (statusOfTheCommitedFile) {
            case "added" -> buildNewFileInformation.processNewFileInformation(gitMetaData);
            case "modified" -> handleModifiedFile.processModificationFileInformation(gitMetaData);
            case "removed" -> handleModifiedFile.handleRemovedFile(gitMetaData);
            default -> throw new FunMarketException("Unknown file status in GitHub metadata: " + statusOfTheCommitedFile);
        };
    }

    public FileInformationBO processFileInformation(FileMetadataBO stockFileDetails, FileClearingBO fileClearing) {
        try {
            FileInformationBO existingFileInformation = findFileInformationByFileName(stockFileDetails.getFileName())
                    .orElse(null);

            if (log.isDebugEnabled()) {
                log.debug("Processing file information for file: {}, StockFileDetails UUID: {}, FileClearing UUID: {}",
                        stockFileDetails.getFileName(), stockFileDetails.getFileUUID(), fileClearing.getFileClearingUuid());
            }

            FileInformationBO processedFileInformation = existingFileInformation == null
                    ? this.processedFileInformation.handleProcessedFileInformationNotFound(stockFileDetails, fileClearing)
                    : this.processedFileInformation.updateExistingFileInformation(existingFileInformation, stockFileDetails, fileClearing);

            FileInformationBO savedFileInformation = this.buildNewFileInformation.saveFileInformation(processedFileInformation);

            if (log.isDebugEnabled()) {
                log.debug("Saved processed FileInformation for file: {}, FileInformation UUID: {}",
                        savedFileInformation.getFileName(), savedFileInformation.getFileInformationUuid());
            }
            return savedFileInformation;
        } catch (Exception e) {
            log.error("Error processing file information for file: {}", stockFileDetails.getFileName(), e);
            throw new RuntimeException("Failed to process file information", e);
        }
    }

    private Optional<FileInformationBO> findFileInformationByFileName(String fileName) {
        try {
            if (fileName == null || fileName.isEmpty()) {
                log.warn("Attempted to find file information with null or empty file name");
                throw new IllegalArgumentException("File name cannot be null or empty");
            }
            return Optional.ofNullable(funMarketFileInformationRetrievalMethods.findByFileName(fileName));
        } catch (Exception e) {
            log.error("Error finding file information by file name: {}", fileName, e);
            throw e;
        }
    }


    private Map<String, Object> removeDotsFromMapKeys(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return map;
        }
        log.info("Removing the file extensions");
        String fileName = getStringValue(map, "fileName", "No File Name").replace(".csv", "");
        map.put("fileName", fileName);

        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            if (entry.getValue() instanceof String) {
                                return entry.getValue().toString().replaceAll("\\.", " ");
                            } else {
                                return entry.getValue();
                            }
                        }
                ));
    }
}
