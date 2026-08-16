package funMarketStockDetails;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import funMarketExceptions.FunMarketException;

import java.util.Map;

public interface FunMarketStockDetailsOperations {

    Response saveStockFileDetails(FileMetadataBO stockFileDetailsBO) throws FunMarketException;
    Response modifyStockFileDetails(FileMetadataBO existingFileDetails, FileMetadataBO FileMetadataBO) throws FunMarketException;
    void removeStockFileDetails(String fileName) throws FunMarketException;

}
