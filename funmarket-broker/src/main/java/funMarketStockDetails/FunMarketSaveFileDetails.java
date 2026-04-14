package funMarketStockDetails;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domins.stockDetails.operations.FunMarketSaveStockFileDetailsMethod;
import com.fsm.domins.stockDetails.operations.FunMarketStockFileDetailsRetrievalMethods;
import funMarketClearing.clearingEngine.FileValidationService;
import funMarketExceptions.FunMarketException;
import funMarketFileInformation.service.FileInformationProcessingEng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static FunMarketUtils.Utils.currentTime;


@Service(value = "funMarketSaveFileDetails")
public class FunMarketSaveFileDetails implements FunMarketStockDetailsOperations {

    private static final Logger log = LoggerFactory.getLogger(FunMarketSaveFileDetails.class);

    private final FunMarketSaveStockFileDetailsMethod funMarketSaveStockFileDetailsMethod;
    private final FunMarketStockFileDetailsRetrievalMethods funMarketStockFileDetailsRetrievalMethods;
    private final FunMarketModifyFileDetails funMarketModifyFileDetails;
    private final FileValidationService fileValidationService;
    private final FileInformationProcessingEng fileInformationProcessingEng;
    public FunMarketSaveFileDetails(@Qualifier(value = "funMarketSaveStockFileDetails") FunMarketSaveStockFileDetailsMethod funMarketSaveStockFileDetailsMethod,
                                    @Qualifier(value = "funMarketStockFileDetailsRetrievals") FunMarketStockFileDetailsRetrievalMethods funMarketStockFileDetailsRetrievalMethods,
                                    @Qualifier(value = "funMarketModifyFileDetails") FunMarketModifyFileDetails funMarketModifyFileDetails,
                                    @Qualifier(value = "fileValidationService") FileValidationService fileValidationService,
                                    @Qualifier(value = "fileInformationProcessingEng") FileInformationProcessingEng fileInformationProcessingEng) {
        this.funMarketSaveStockFileDetailsMethod = funMarketSaveStockFileDetailsMethod;
        this.funMarketStockFileDetailsRetrievalMethods = funMarketStockFileDetailsRetrievalMethods;
        this.funMarketModifyFileDetails = funMarketModifyFileDetails;
        this.fileValidationService = fileValidationService;
        this.fileInformationProcessingEng = fileInformationProcessingEng;
    }


    @Override
    @Transactional
    public FileMetadataBO saveStockFileDetails(FileMetadataBO stockFileDetailsBO) throws FunMarketException {

        if (stockFileDetailsBO == null) {
            log.info("Input object cannot be null input Object: [ {} ]", FileMetadataBO.class.getSimpleName());
            throw new FunMarketException("Input object cannot be null input Object [ " + FileMetadataBO.class.getSimpleName() + " ]");
        }
        final String fileName = stockFileDetailsBO.getFileName();
        final LocalDateTime currentTime = currentTime();
        log.info("Instating file clearing, FileName: [ {} ]", fileName);
        log.info("Checking file details, with FileName: [ {} ], Time: [ {} ]", fileName, currentTime);
        FileMetadataBO existingDetails = funMarketStockFileDetailsRetrievalMethods.findByFileName(fileName);
        if (existingDetails != null && existingDetails.getFileUUID() != null) {
            log.info("File details found, with FileName: [ {} ] Instating fileModification Time [ {} ]", fileName, currentTime);
            stockFileDetailsBO.setRecordStatusBO(RecordStatusBO.MODIFIED);
            stockFileDetailsBO = fileModification(existingDetails, stockFileDetailsBO);
            stockFileDetailsBO = saveFileDetails(stockFileDetailsBO); //saving file information
            if(isValid(stockFileDetailsBO)){
                return stockFileDetailsBO;
            }else{
                log.info("Final Validation modification fail withe UUID: [ {} ], FileName: [ {} ]. RollBacking the fileMetaData Transaction.",stockFileDetailsBO.getFileUUID(), stockFileDetailsBO.getFileName());
                throw new FunMarketException("Saved invalid details. RollBacking the file metadata");
            }
        }
        log.info("No fileDetails found with fileName: {} creating new file object", fileName);
        buildStockFileDetails(stockFileDetailsBO, currentTime);
        FileClearingBO fileClearing = fileValidationService.initiatingFileClearingProcess(stockFileDetailsBO);
        log.info("File Clearing process completed for the fileName: [ {} ], FileClearing Status: [ {} ], FileClearing code [ {} ], fileClearing Reason [ {} ]", fileName, fileClearing.getClearingCode(),fileClearing.getClearingCode(), fileClearing.getClearingMessage());
        stockFileDetailsBO.setValidationStatus(fileClearing.getFileValidationStatus());
        if (stockFileDetailsBO.getFileUUID() != null) {
            stockFileDetailsBO = saveFileDetails(stockFileDetailsBO);
            log.info("File Detail Saved successfully with UUID: [ {} ], FileName: [ {} ], Time: [ {} ]", stockFileDetailsBO.getFileUUID(), fileName, currentTime);
            FileInformationBO informationBO = fileInformationProcessingEng.processFileInformation(stockFileDetailsBO, fileClearing);
            log.info("File information processed with status [ {} ] for FileName: [ {} ]", informationBO.getFileInformationStatus(), stockFileDetailsBO.getFileName());
        } else {
            log.info("Cannot process the File Details, File UUID created Null");
            throw new FunMarketException("Cannot process the File Details, File UUID created Null");
        }
        if(isValid(stockFileDetailsBO)){
            return stockFileDetailsBO;
        }else{
            log.info("Final Validation saving fail withe UUID: [ {} ], FileName: [ {} ]. RollBacking the fileMetaData Transaction.",stockFileDetailsBO.getFileUUID(), stockFileDetailsBO.getFileName());
            throw new FunMarketException("Saved invalid details. RollBacking the file metadata");
        }
    }

    private FileMetadataBO fileModification(FileMetadataBO existingDetails, FileMetadataBO stockFileDetailsBO) {
        log.info("File details send to modification time: [ {} ]", currentTime());
        return funMarketModifyFileDetails.modifyStockFileDetails(existingDetails, stockFileDetailsBO);
    }

    private void buildStockFileDetails(FileMetadataBO stockFileDetailsBO, LocalDateTime currentTime) {
        log.info("Building file details object with current time: [ {} ], FileName: [ {} ], RecordStatus: [ ADDED ]", currentTime, stockFileDetailsBO.getFileName());
        final String uuid = UUID.randomUUID().toString();
        stockFileDetailsBO.setFileUUID(uuid);
        stockFileDetailsBO.setFileUploadDate(currentTime);
        stockFileDetailsBO.setFileModifiedDate(currentTime);
        stockFileDetailsBO.setRecordStatusBO(RecordStatusBO.ADDED);
    }

    private FileMetadataBO saveFileDetails(FileMetadataBO stockFileDetailsBO) {
        log.info("Saving file Details. FileName: [ {} ], Time: [ {} ]", stockFileDetailsBO.getFileName(), currentTime());
        return funMarketSaveStockFileDetailsMethod.saveStockFileDetails(stockFileDetailsBO);
    }

    private boolean isValid(FileMetadataBO savedFileDetails){
        log.info("Final Validation withe UUID: [ {} ], FileName: [ {} ]",savedFileDetails.getFileUUID(), savedFileDetails.getFileName());
        return savedFileDetails.getFileName() != null && savedFileDetails.getFileUUID() != null && !savedFileDetails.getFileUUID().isBlank() && !savedFileDetails.getFileName().isBlank();
    }


    @Override
    public FileMetadataBO modifyStockFileDetails(FileMetadataBO existingFileDetails, FileMetadataBO FileMetadataBO) throws FunMarketException {
        log.info("Cannot Do file modification directly.. [ {} ]", FunMarketSaveFileDetails.class);
        throw new FunMarketException("Cannot Do file modification directly.. [ " + FunMarketSaveFileDetails.class + " ]. Modification fail");
    }

    @Override
    public void removeStockFileDetails(String fileName) throws FunMarketException {
        log.info("Cannot do remove Stock stock details... [ {} ]", FunMarketSaveFileDetails.class);
        throw new FunMarketException("Cannot do remove Stock stock details... [ " + FunMarketSaveFileDetails.class + " ] Deletion Fail");
    }
}
