package funMarketClearing.clearingEngine.validation;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;

public abstract class FileValidationAbstractService {

    public abstract void process(FileMetadataBO stockFileDetailsBO);
}
