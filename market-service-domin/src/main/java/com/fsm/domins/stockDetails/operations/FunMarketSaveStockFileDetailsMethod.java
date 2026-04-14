package com.fsm.domins.stockDetails.operations;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import org.springframework.stereotype.Component;

@Component(value = "funMarketSaveStockFileDetailsMethod")
public sealed interface FunMarketSaveStockFileDetailsMethod permits FunMarketSaveStockFileDetails {

    FileMetadataBO saveStockFileDetails(FileMetadataBO stockFileDetailsBO);
}
