package funMarketFileInformation.operations;

import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domins.information.operations.FunMarketSaveFileInformation;
import funMarketExceptions.FunMarketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "saveFileInformation")
public class FunMarketSaveFileInformationBO implements FileInformationOperationsManagerBO {

    private static final Logger log = LoggerFactory.getLogger(FunMarketSaveFileInformationBO.class);

    private final FunMarketSaveFileInformation funMarketSaveFileInformation;

    public FunMarketSaveFileInformationBO(@Qualifier(value = "funMarketSaveFileInformation") FunMarketSaveFileInformation funMarketSaveFileInformation) {
        this.funMarketSaveFileInformation = funMarketSaveFileInformation;
    }

    @Override
    @Transactional
    public FileInformationBO saveFileInformationMetaData(FileInformationBO fileInformationBo) {
        if(isValid(fileInformationBo)){
            try{
                funMarketSaveFileInformation.saveFileInformation(fileInformationBo);
                log.info("Successfully saved file information with [ UUID: {}, FileName: {} ]", fileInformationBo.getFileInformationUuid(), fileInformationBo.getFileName());
                return fileInformationBo;
            }catch (Exception e){
                log.error("Error saving file information with [ UUID: {}, FileName: {} ]", fileInformationBo.getFileInformationUuid(), fileInformationBo.getFileName(), e);
                throw new RuntimeException("Failed to save file information", e);
            }
        }
        throw new FunMarketException("FileInformationBO is not valid. className: [ " + FunMarketSaveFileInformationBO.class.getSimpleName() + " ]");
    }

    public boolean isValid(FileInformationBO fileInformationBO) {
        if (fileInformationBO == null) {
            log.info("FileInformationBO is null. className: [ {} ]", FunMarketSaveFileInformationBO.class.getSimpleName());
            return false;
        }
        if (fileInformationBO.getFileInformationUuid() == null || fileInformationBO.getFileInformationUuid().isEmpty()) {
            log.info("FileInformationBO fileInformationUuid is null or empty. className: [ {} ]", FunMarketSaveFileInformationBO.class.getSimpleName());
            return false;
        }
        if (fileInformationBO.getFileName() == null || fileInformationBO.getFileName().isEmpty()) {
            log.info("FileInformationBO fileName is null or empty. className: [ {} ]", FunMarketSaveFileInformationBO.class.getSimpleName());
            return false;
        }
        if (fileInformationBO.getFileStockDetailsUuid() == null || fileInformationBO.getFileStockDetailsUuid().isBlank() || fileInformationBO.getFileStockDetailsUuid().isEmpty()) {
            log.info("FileInformationBO fileStockDetailsUuid is null or empty. className: [ {} ]", FunMarketSaveFileInformationBO.class.getSimpleName());
            return false;
        }
        return true;
    }

    @Override
    public FileInformationBO modifyFileInformationMetaData(FileInformationBO fileInformationBO) {
        log.info("Cannot happen directly hear. className: [ {} ]", FunMarketSaveFileInformationBO.class.getSimpleName());
        throw new FunMarketException("Cannot happen directly hear. className: [ " + FunMarketSaveFileInformationBO.class.getSimpleName() + " ]");
    }
}
