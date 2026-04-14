package com.fsm.domins.clearing.operations;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domins.clearing.mapper.FileClearingMapper;
import com.fsm.domins.clearing.models.FileClearing;
import com.fsm.domins.clearing.repository.FileClearingRepo;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

@Component(value = "funMarketFileClearingRetrievals")
@RequiredArgsConstructor
public final class FunMarketFileClearingRetrievals implements FileClearingRetrievalMethods {

    private final Logger log = LoggerFactory.getLogger(FunMarketFileClearingRetrievals.class);
    private final FileClearingRepo fileClearingRepo;

    @Override
    public FileClearingBO findByFileClearingUuid(String fileClearingUuid) {
        Optional<FileClearing> fileClearing = fileClearingRepo.findById(fileClearingUuid);
        return fileClearing.map(FileClearingMapper::FileClearingToBO).orElse(null);
    }

    @Override
    public FileClearingBO findByFileUuid(String fileUuid) {
        FileClearing fileClearing = fileClearingRepo.findByFileUuid(fileUuid);
        return fileClearing != null ? FileClearingMapper.FileClearingToBO(fileClearing) : null;
    }

    @Override
    public List<FileClearingBO> findAll() {
        List<FileClearing> fileClearing = fileClearingRepo.findAll();
        return fileClearing.stream().map(FileClearingMapper::FileClearingToBO).toList();
    }

    public List<String> getFileNamesByValidationStatus(String status){

        if(status.isBlank()){
            log.warn("Status Cannot be null Status");
            throw new IllegalArgumentException("The Status cannot be null");
        }
        List<FileClearingBO> allValidationFiles = findAll();
        String finalStatus = status.toUpperCase();
        List<String> clearedFileNames = allValidationFiles.stream()
              .collect(Collectors.groupingBy(FileClearingBO::getFileName))
              .values().stream()
              .map(files -> files.stream()
                      .max(Comparator.comparing(FileClearingBO::getClearingDate))
                      .orElse(null))
              .filter(Objects::nonNull)
              .filter(file -> file.getFileValidationStatus().equals(status))
              .map(FileClearingBO::getFileName)
              .collect(Collectors.toList());

            log.info("Found {} file with {} status", clearedFileNames.size(), finalStatus);
            if(!clearedFileNames.isEmpty()){
                return clearedFileNames;
            }
            return List.of();
        }
}






