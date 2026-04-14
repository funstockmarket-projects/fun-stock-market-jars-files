package com.fsm.domins.information.operations;

import com.fsm.domins.information.mapper.FileInformationMapper;
import com.fsm.domins.information.models.FileInformation;
import com.fsm.domins.information.repository.FileInformationRepo;
import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "funMarketSaveFileInformation")
@RequiredArgsConstructor
public final class FunMarketSaveFileInformation implements FunMarketSaveFileInformationMethod {

    private static final Logger log = LoggerFactory.getLogger(FunMarketSaveFileInformation.class);

    private final FileInformationRepo fileInformationRepo;

    @Override
    public FileInformationBO saveFileInformation(FileInformationBO fileInformationBO) {
        log.info("Saving file information with UUID: {}", fileInformationBO.getFileInformationUuid());
        FileInformation fileInformation;
        try {
            fileInformation = FileInformationMapper.bOToFileInformation(fileInformationBO);
            fileInformation = fileInformationRepo.save(fileInformation);
            log.info("Successfully Saving file information with [ UUID: {}, FileName: {} ]", fileInformation.fileInformationUuid(), fileInformationBO.getFileName());
        } catch (Exception e) {
            log.error("Error Saving file information with [ UUID: {} ]", fileInformationBO.getFileInformationUuid(), e);
            throw new RuntimeException("Failed to modify file information", e);
        }

        return FileInformationMapper.fileInformationToBO(fileInformation);
    }
}
