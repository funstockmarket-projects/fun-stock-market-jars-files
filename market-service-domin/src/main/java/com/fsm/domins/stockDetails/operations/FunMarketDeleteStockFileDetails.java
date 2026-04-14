package com.fsm.domins.stockDetails.operations;

import com.fsm.domins.stockDetails.repository.MarketFileDataRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "funMarketDeleteStockFileDetails")
@RequiredArgsConstructor
public final class FunMarketDeleteStockFileDetails implements FunMarketStockFileDetailsRemoveMethods {

    private static final Logger log = LoggerFactory.getLogger(FunMarketDeleteStockFileDetails.class);

    private final MarketFileDataRepo marketFileDataRepo;

    @Override
    public void removeStockFileDetailsByUUID(String fileUUID) {
        if (fileUUID == null || fileUUID.isBlank()) {
            throw new IllegalArgumentException("File UUID cannot be null or blank. Failed to delete stock file details.");
        }
        marketFileDataRepo.deleteById(fileUUID);
        log.info("Successfully deleted stock file details with [ UUID: {} ]", fileUUID);
    }
}
