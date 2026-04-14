package com.fsm.domins.stockDetails.operations;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domins.stockDetails.mapper.StockFileDetailsMapper;
import com.fsm.domins.stockDetails.models.FileMetadata;
import com.fsm.domins.stockDetails.repository.MarketFileDataRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "funMarketStockFileDetailsRetrievals")
@RequiredArgsConstructor
public final class FunMarketStockFileDetailsRetrievals implements FunMarketStockFileDetailsRetrievalMethods {

    private final MarketFileDataRepo marketFileDataRepo;

    @Override
    public FileMetadataBO findByFileUUID(String fileUUID) {
        Optional<FileMetadata> stockFileDetails = marketFileDataRepo.findById(fileUUID);
        return stockFileDetails.map(StockFileDetailsMapper::stockFileDetailsToBO).orElse(null);
    }

    @Override
    public FileMetadataBO findByFileName(String fileName) {
        Optional<FileMetadata> stockFileDetails = marketFileDataRepo.findByFileName(fileName);
        return stockFileDetails.map(StockFileDetailsMapper::stockFileDetailsToBO).orElse(null);
    }

    @Override
    public List<FileMetadataBO> findAll() {
        List<FileMetadata> fileMetadataList = marketFileDataRepo.findAll();
        return fileMetadataList.stream().map(StockFileDetailsMapper::stockFileDetailsToBO).toList();
    }
}
