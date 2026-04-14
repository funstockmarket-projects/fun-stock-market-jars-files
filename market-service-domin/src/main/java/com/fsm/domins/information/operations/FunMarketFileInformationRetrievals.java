package com.fsm.domins.information.operations;

import com.fsm.domins.information.mapper.FileInformationMapper;
import com.fsm.domins.information.models.FileInformation;
import com.fsm.domins.information.repository.FileInformationRepo;
import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component(value = "funMarketFileInformationRetrievals")
@RequiredArgsConstructor
public final class FunMarketFileInformationRetrievals implements FunMarketFileInformationRetrievalMethods {

    private final FileInformationRepo fileInformationRepo;

    @Override
    public FileInformationBO findByFileInformationUuid(String fileInformationUuid) {
        Optional<FileInformation> fileInformation = fileInformationRepo.findById(fileInformationUuid);
        return fileInformation.map(FileInformationMapper::fileInformationToBO).orElse(null);
    }

    @Override
    public FileInformationBO findByFileName(String fileName) {
        Optional<FileInformation> fileInformation = fileInformationRepo.findByFileName(fileName);
        return fileInformation.map(FileInformationMapper::fileInformationToBO).orElse(null);
    }

    @Override
    public List<FileInformationBO> findAll() {
        List<FileInformation> fileInformations = fileInformationRepo.findAll();
        return fileInformations.stream().map(FileInformationMapper::fileInformationToBO).toList();
    }
}
