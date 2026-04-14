package com.fsm.domins.information.methodes;

import com.fsm.domins.clearing.models.FileClearing;
import com.fsm.domins.information.models.FileInformation;
import com.fsm.domins.stockDetails.models.FileMetadata;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FileInformationMethod {
    public Object preProcessFileInformation(List<Map<String, Object>> githubEvent);
    public FileInformation processFileInformation(FileMetadata fileMetadata, FileClearing fileClearing );
    public FileInformation postProcessFileInformation(String fileName, Map<String, Object> gitMetadata, String statusOfTheCommitedFile) ;
    Optional<FileInformation> findFileInformationByFileName(String fileName);
}
