package com.fsm.domins.stockDetails.operations.funMarketStockDetails;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.StockDetailsBO;
import com.fsm.domins.stockDetails.mapper.StockDetailsMapper;
import com.fsm.domins.stockDetails.models.StockDetails;
import com.fsm.domins.stockDetails.repository.StockDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service(value = "funMarketStockServicesImportations")
public class FunMarketStockServicesImportations {

    private static final Logger log = LoggerFactory.getLogger(FunMarketStockServicesImportations.class);
    private final StockDetailsRepository stockDetailsRepository;

    public FunMarketStockServicesImportations(@Qualifier(value = "stockDetailsRepository") StockDetailsRepository stockDetailsRepository) {
        this.stockDetailsRepository = stockDetailsRepository;
    }

    @Transactional
    public StockDetailsBO saveStockDetails(StockDetailsBO stockDetailsBO) {
        if (stockDetailsBO == null) {
            log.error("StockDetailsBO is null. Cannot save stock details.");
            return null;
        }
        StockDetails details = StockDetailsMapper.bOToStockDetails(stockDetailsBO);
        StockDetails savedDetails = stockDetailsRepository.save(details);
        log.info("Stock details saved successfully for stockId: {}", savedDetails.stockId());
        return StockDetailsMapper.stockDetailsToBO(savedDetails);
    }

    public Optional<StockDetailsBO> findByStockId(String stockId) {
        if(stockId.isBlank()){
            log.error("StockId is blank. Cannot find stock details.");
            return Optional.empty();
        }
        stockDetailsRepository.findByStockId(stockId).orElseThrow(() -> {
            log.error("Stock details not found for stockId: {}", stockId);
            return new RuntimeException("Stock details not found for stockId: " + stockId);
        });
         return stockDetailsRepository.findByStockId(stockId).map(StockDetailsMapper::stockDetailsToBO);
    }

    public Optional<StockDetailsBO> findByStockSymbol(String stockSymbol) {
        if(stockSymbol.isBlank()){
            log.error("StockSymbol is blank. Cannot find stock details.");
            return Optional.empty();
        }
        stockDetailsRepository.findByStockSymbol(stockSymbol).orElseThrow(() -> {
            log.error("Stock details not found for stockSymbol: {}", stockSymbol);
            return new RuntimeException("Stock details not found for stockSymbol: " + stockSymbol);
        });
        return stockDetailsRepository.findByStockSymbol(stockSymbol).map(StockDetailsMapper::stockDetailsToBO);
    }

    public Optional<StockDetailsBO> findByStockName(String stockName) {
        if(stockName.isBlank()){
            log.error("StockName is blank. Cannot find stock details.");
            return Optional.empty();
        }
        stockDetailsRepository.findByStockName(stockName).orElseThrow(() -> {
            log.error("Stock details not found for stockName: {}", stockName);
            return new RuntimeException("Stock details not found for stockName: " + stockName);
        });
        return stockDetailsRepository.findByStockName(stockName).map(StockDetailsMapper::stockDetailsToBO);
    }
}
