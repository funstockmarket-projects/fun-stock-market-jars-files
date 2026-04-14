package funMarketStockDetails;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import funMarketExceptions.FunMarketException;

public interface FunMarketStockDetailsOperations {

    FileMetadataBO saveStockFileDetails(FileMetadataBO stockFileDetailsBO) throws FunMarketException;
    FileMetadataBO modifyStockFileDetails(FileMetadataBO existingFileDetails, FileMetadataBO FileMetadataBO) throws FunMarketException;
    void removeStockFileDetails(String fileName) throws FunMarketException;

}
