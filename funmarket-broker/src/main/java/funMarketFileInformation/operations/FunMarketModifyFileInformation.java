package funMarketFileInformation.operations;

import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import funMarketExceptions.FunMarketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service(value = "funMarketModifyFileInformation")
public class FunMarketModifyFileInformation implements FileInformationOperationsManagerBO {

    private static final Logger log = LoggerFactory.getLogger(FunMarketModifyFileInformation.class);

    private final FunMarketSaveFileInformationBO fileInformationService;

    public FunMarketModifyFileInformation(FunMarketSaveFileInformationBO fileInformationService){
        this.fileInformationService = fileInformationService;
    }
    @Transactional
    public FileInformationBO modifyFileInformation(FileInformationBO fileInformationBO) {
        try{
            return modifyFileInformationMetaData(fileInformationBO);
        }catch(FunMarketException e){
            log.info("Modification failed, FileName: [ {} ], reason: [ {} ]", fileInformationBO.getFileName(), e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public FileInformationBO modifyFileInformationMetaData(FileInformationBO fileInformationBO) {
        if(isValid(fileInformationBO)){
            log.info("Ready to save modification data, FileName: [ {} ]", fileInformationBO.getFileName());
            return saveFileInformationMetaData(fileInformationBO);
        }else{
            log.info("Cannot do modification until conditions match [ input data cannot be null, RecordStatus must be 'MODIFIED', file status must be 'MODIFIED' ]");
            throw new FunMarketException("Cannot do modification until conditions match [ input data cannot be null, RecordStatus must be 'MODIFIED', file status must be 'MODIFIED' ]");
        }
    }

    private boolean isValid(FileInformationBO fileInformationBO){
        // Validate object is not null
        if(Objects.isNull(fileInformationBO)){
            log.error("FileInformationBO is null");
            return false;
        }
        if(fileInformationBO.getGitHubFileStatus() == null || !fileInformationBO.getGitHubFileStatus().equals(RecordStatusBO.MODIFIED)){
            log.error("GitHubFileInToSubBranchPusherName is null or empty, FileName: [ {} ]", fileInformationBO.getFileName());
            return false;
        }
        if(fileInformationBO.getRecordStatusBO() == null || !fileInformationBO.getRecordStatusBO().equals(RecordStatusBO.MODIFIED)){
            log.error("FileInformationBO RecordStatus must be MODIFIED, current status: [ {} ]", fileInformationBO.getRecordStatusBO());
            return false;
        }
        return true;
    }

    @Override
    public FileInformationBO saveFileInformationMetaData(FileInformationBO fileInformationBO) {
        if(!fileInformationBO.getRecordStatusBO().equals(RecordStatusBO.MODIFIED)){
            log.error("Cannot modify file information with status other than MODIFIED. Current status: [ {} ]", fileInformationBO.getRecordStatusBO());
            throw new FunMarketException("Cannot happen directly here. className: [ " + FunMarketModifyFileInformation.class.getSimpleName() + " ]");
        }
        return fileInformationService.saveFileInformationMetaData(fileInformationBO);
    }

}
