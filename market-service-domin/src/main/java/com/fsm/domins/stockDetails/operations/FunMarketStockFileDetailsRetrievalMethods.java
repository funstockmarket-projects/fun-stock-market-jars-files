package com.fsm.domins.stockDetails.operations;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "funMarketStockFileDetailsRetrievalMethods")
public sealed interface FunMarketStockFileDetailsRetrievalMethods permits FunMarketStockFileDetailsRetrievals {

    FileMetadataBO findByFileUUID(String fileUUID);

    FileMetadataBO findByFileName(String fileName);

    List<FileMetadataBO> findAll();
}
