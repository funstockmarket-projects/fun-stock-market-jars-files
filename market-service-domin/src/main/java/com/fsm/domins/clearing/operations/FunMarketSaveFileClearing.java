package com.fsm.domins.clearing.operations;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domins.clearing.mapper.FileClearingMapper;
import com.fsm.domins.clearing.models.FileClearing;
import com.fsm.domins.clearing.repository.FileClearingRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "funMarketSaveFileClearing")
@RequiredArgsConstructor
public final class FunMarketSaveFileClearing implements FileClearingSaveOperations {

    private static final Logger log = LoggerFactory.getLogger(FunMarketSaveFileClearing.class);

    private final FileClearingRepo fileClearingRepo;

    @Override
    public FileClearingBO save(FileClearingBO fileClearingBO) {
        log.info("Saving file clearing with identifier: {}", fileClearingBO.getFileClearingUuid());

        FileClearing fileClearing = FileClearingMapper.bOToFileClearing(fileClearingBO);

        try {
            fileClearing = fileClearingRepo.save(fileClearing);
            log.info("Successfully saved file clearing with [ UUID: {}, FileName: {} ]", fileClearing.fileClearingUuid(), fileClearing.fileName());
        } catch (Exception e) {
            log.error("Error saving file clearing with [ identifier: {} ]", fileClearing.fileClearingUuid(), e);
            throw new RuntimeException("Failed to save file clearing", e);
        }

        return FileClearingMapper.FileClearingToBO(fileClearing);
    }
}






