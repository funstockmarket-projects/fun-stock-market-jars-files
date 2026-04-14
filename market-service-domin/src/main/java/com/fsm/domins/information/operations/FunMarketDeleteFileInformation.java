package com.fsm.domins.information.operations;

import com.fsm.domins.information.repository.FileInformationRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "funMarketDeleteFileInformation")
@RequiredArgsConstructor
public final class FunMarketDeleteFileInformation implements FunMarketFileInformationRemoveMethods {

    private static final Logger log = LoggerFactory.getLogger(FunMarketDeleteFileInformation.class);

    private final FileInformationRepo fileInformationRepo;

    @Override
    public void removeFileInformationByUuid(String fileInformationUuid) {
        if (fileInformationUuid == null || fileInformationUuid.isBlank()) {
            throw new IllegalArgumentException("File Information UUID cannot be null or blank. Failed to delete file information.");
        }
        fileInformationRepo.deleteById(fileInformationUuid);
        log.info("Successfully deleted file information with [ UUID: {} ]", fileInformationUuid);
    }
}
