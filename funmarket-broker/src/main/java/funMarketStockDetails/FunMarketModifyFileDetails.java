package funMarketStockDetails;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import funMarketClearing.clearingEngine.FileValidationService;
import funMarketExceptions.FunMarketException;
import funMarketFileInformation.service.FileInformationProcessingEng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Service(value = "funMarketModifyFileDetails")
public class FunMarketModifyFileDetails implements FunMarketStockDetailsOperations {

    private final Logger log = LoggerFactory.getLogger(FunMarketModifyFileDetails.class);

    private static final String TIMEZONE_ASIA_KOLKATA = "Asia/Kolkata";

    private final FileValidationService fileValidationService;
    private final FileInformationProcessingEng fileInformationProcessingEng;



    public FunMarketModifyFileDetails(@Qualifier(value = "fileValidationService") FileValidationService fileValidationService,
                                      @Qualifier(value = "fileInformationProcessingEng")FileInformationProcessingEng fileInformationProcessingEng) {
        this.fileValidationService = fileValidationService;
        this.fileInformationProcessingEng = fileInformationProcessingEng;
    }

    @Override
    public Response modifyStockFileDetails(FileMetadataBO existingFileDetails, FileMetadataBO stockFileDetailsBO) {
        log.error("Performing file modification");
        final LocalDateTime currentTime = currentTime();
        if (stockFileDetailsBO == null || existingFileDetails == null) {
            log.error("Input Cannot be null. existingFileDetails or stockFileDetailsBO is null Time [ {} ]", currentTime);
            throw new FunMarketException("Input Cannot be null. existingFileDetails or stockFileDetailsBO is null [ " + currentTime + " ]");
        }
        prepareObject(existingFileDetails, stockFileDetailsBO);
        FileClearingBO clearingBO = fileValidationService.initiatingFileClearingProcess(existingFileDetails);
        if(clearingBO.getFileClearingUuid() != null){
            log.info("File clearing process completed with UUID [ {} ] for FileName: [ {} ]", clearingBO.getFileClearingUuid(), stockFileDetailsBO.getFileName());
            existingFileDetails.setValidationStatus(clearingBO.getFileValidationStatus());
            existingFileDetails.setValidationMessage(clearingBO.getClearingMessage());
        }else{
            existingFileDetails.setValidationStatus(" ");
            existingFileDetails.setValidationMessage(" ");
             log.info("File clearing process completed with status [ {} ] for FileName: [ {} ]", clearingBO.getFileValidationStatus(), stockFileDetailsBO.getFileName());
        }
        FileInformationBO informationBO = fileInformationProcessingEng.processFileInformation(existingFileDetails, clearingBO);
        if(informationBO.getFileInformationUuid() != null){
             log.info("File information processing completed with UUID [ {} ] for FileName: [ {} ]", informationBO.getFileInformationUuid(), stockFileDetailsBO.getFileName());
             existingFileDetails.setFileInformationUUID(informationBO.getFileInformationUuid());
             existingFileDetails.setFileInformationRecordStatus(informationBO.getRecordStatusBO());
        }

        log.info("File information processed with status [ {} ] for FileName: [ {} ]", informationBO.getFileInformationStatus(), stockFileDetailsBO.getFileName());
        final String fileName = stockFileDetailsBO.getFileName();
        log.info("Clearing successful with status status codes at Time [ {} ], for FileName: [ {} ]", currentTime, fileName);
        return new Response(existingFileDetails, informationBO.getFileProcessingNumberOfCount());
    }

    private void prepareObject(FileMetadataBO existingFileDetails, FileMetadataBO stockFileDetailsBO) {
        log.info("Transferring data from input object to existing object for FileName: [ {} ]", stockFileDetailsBO.getFileName());
        existingFileDetails.setFolderName(stockFileDetailsBO.getFolderName());
        existingFileDetails.setFileType(stockFileDetailsBO.getFileType());
        existingFileDetails.setFileSize(stockFileDetailsBO.getFileSize());
        existingFileDetails.setNumberOfRecords(stockFileDetailsBO.getNumberOfRecords());
        existingFileDetails.setUri(stockFileDetailsBO.getUri());
        existingFileDetails.setEventNameBO(stockFileDetailsBO.getEventNameBO());
        existingFileDetails.setFileModifiedDate(currentTime());
        existingFileDetails.setFileData(stockFileDetailsBO.getFileData());
        existingFileDetails.setRecordStatusBO(stockFileDetailsBO.getRecordStatusBO());
        existingFileDetails.setValidationStatus(stockFileDetailsBO.getValidationStatus());
    }

    private static LocalDateTime currentTime() {
        return LocalDateTime.now(ZoneId.of(TIMEZONE_ASIA_KOLKATA));
    }

    @Override
    public Response saveStockFileDetails(FileMetadataBO stockFileDetailsBO) throws FunMarketException {
        log.error("Cannot perform save operation hear [ {} ]", FunMarketModifyFileDetails.class);
        throw new FunMarketException("Cannot perform save operation hear [ " + FunMarketModifyFileDetails.class + " ]. Cannot perform save operation hear.");
    }

    @Override
    public void removeStockFileDetails(String fileName) throws FunMarketException {
        log.error("Cannot perform save or remove hear [ {} ]", FunMarketModifyFileDetails.class);
        throw new FunMarketException("Cannot perform save operation hear [ " + FunMarketModifyFileDetails.class + " ]. Cannot perform remove operation hear.");
    }
}
