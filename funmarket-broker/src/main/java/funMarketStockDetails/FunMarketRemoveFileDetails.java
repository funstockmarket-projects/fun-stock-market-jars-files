package funMarketStockDetails;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domins.stockDetails.operations.FunMarketStockFileDetailsRemoveMethods;
import com.fsm.domins.stockDetails.operations.FunMarketStockFileDetailsRetrievalMethods;
import funMarketExceptions.FunMarketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service(value="funMarketRemoveFileDetails")
public class FunMarketRemoveFileDetails implements FunMarketStockDetailsOperations {

    private final Logger log = LoggerFactory.getLogger(FunMarketRemoveFileDetails.class);

    private final FunMarketStockFileDetailsRemoveMethods funMarketStockFileDetailsRemoveMethods;
    private final FunMarketStockFileDetailsRetrievalMethods funMarketStockFileDetailsRetrievalMethods;

    public FunMarketRemoveFileDetails( @Qualifier(value = "funMarketDeleteStockFileDetails") FunMarketStockFileDetailsRemoveMethods funMarketStockFileDetailsRemoveMethods,
                                       @Qualifier(value = "funMarketStockFileDetailsRetrievals") FunMarketStockFileDetailsRetrievalMethods funMarketStockFileDetailsRetrievalMethods){
        this.funMarketStockFileDetailsRemoveMethods = funMarketStockFileDetailsRemoveMethods;
        this.funMarketStockFileDetailsRetrievalMethods =funMarketStockFileDetailsRetrievalMethods;
    }

    @Override
    public void removeStockFileDetails(String fileName) {

        if(fileName.isBlank()){
            log.info("File name cannot be null..");
            throw new FunMarketException("File name cannot be null..");
        }
        log.info("Finding by file name. FileName [ {} ]", fileName);
        FileMetadataBO stockFileDetailsBO = funMarketStockFileDetailsRetrievalMethods.findByFileName(fileName);
        if(stockFileDetailsBO != null && stockFileDetailsBO.getFileUUID().isBlank()){
            log.info("Found the file details. with FileName: [ {} ], deleting by UUID: [ {} ], ", fileName, stockFileDetailsBO.getFileUUID());
            funMarketStockFileDetailsRemoveMethods.removeStockFileDetailsByUUID(stockFileDetailsBO.getFileUUID());
            log.info("Successfully deleted by UUID: [ {} ], Date [ {} ]", stockFileDetailsBO.getFileUUID(), LocalDate.now());
        }
    }

    @Override
    public Response saveStockFileDetails(FileMetadataBO stockFileDetailsBO) throws FunMarketException {
        log.error("Cannot perform save operation hear [ {} ]", FunMarketModifyFileDetails.class);
        throw new FunMarketException("Cannot perform save operation hear [ "+FunMarketRemoveFileDetails.class+" ]. Cannot perform save operation hear.");
    }

    @Override
    public Response  modifyStockFileDetails(FileMetadataBO existingFileDetails, FileMetadataBO FileMetadataBO) throws FunMarketException {
        log.error("Cannot perform modification operation hear [ {} ]", FunMarketModifyFileDetails.class);
        throw new FunMarketException("Cannot perform remove operation hear [ "+FunMarketRemoveFileDetails.class+" ]. Cannot perform modification operation hear.");
    }
}
