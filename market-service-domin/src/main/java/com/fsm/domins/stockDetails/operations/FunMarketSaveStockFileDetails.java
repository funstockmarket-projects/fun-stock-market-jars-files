package com.fsm.domins.stockDetails.operations;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domins.stockDetails.mapper.StockFileDetailsMapper;
import com.fsm.domins.stockDetails.models.FileMetadata;
import com.fsm.domins.stockDetails.repository.MarketFileDataRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "funMarketSaveStockFileDetails")
@RequiredArgsConstructor
public final class FunMarketSaveStockFileDetails implements FunMarketSaveStockFileDetailsMethod {

    private static final Logger log = LoggerFactory.getLogger(FunMarketSaveStockFileDetails.class);

    private final MarketFileDataRepo marketFileDataRepo;

    @Override
    public FileMetadataBO saveStockFileDetails(FileMetadataBO stockFileDetailsBO) {
        log.info("Saving stock file details with file name: {}", stockFileDetailsBO.getFileName());

        FileMetadata fileMetadata = StockFileDetailsMapper.bOToStockFileDetails(stockFileDetailsBO);

        try {
            fileMetadata = marketFileDataRepo.save(fileMetadata);
            log.info("Successfully saved stock file details with [ UUID: {}, FileName: {} ]", fileMetadata.fileUUID(), fileMetadata.fileName());
        } catch (Exception e) {
            log.error("Error saving stock file details with [ file name: {} ]", stockFileDetailsBO.getFileName(), e);
            throw new RuntimeException("Failed to save stock file details", e);
        }

        return StockFileDetailsMapper.stockFileDetailsToBO(fileMetadata);
    }
}
