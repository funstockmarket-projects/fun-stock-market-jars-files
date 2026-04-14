package com.fsm.domins.clearing.service;

import com.fsm.domins.clearing.models.FileClearing;

public class FileClearingDominService {

    public FileClearing processFileClearing(FileClearing fileClearing) {

        if(fileClearing == null) {
            throw new IllegalArgumentException("FileClearing object cannot be null");
        }
        if(fileClearing.fileClearingUuid() == null || fileClearing.fileClearingUuid().isBlank()) {
            throw new IllegalArgumentException("File UUID cannot be null or blank");
        }
        if(fileClearing.clearingCode() == null || fileClearing.clearingCode().isBlank()) {
            throw new IllegalArgumentException("Clearing code cannot be null or blank");
        }
        String status = fileClearing.fileValidationStatus();
        if(status == null || status.isBlank() || !status.equals("CLEARED") && !status.equals("REJECTED") && !status.equals("NOT_CLEARED")) {
            throw new IllegalArgumentException("File validation status cannot be null or blank");
        }
        if(fileClearing.clearingMessage() == null || fileClearing.clearingMessage().isBlank()) {
            throw new IllegalArgumentException("Clearing message cannot be null or blank");
        }
        if(fileClearing.clearingDate() == null) {
            throw new IllegalArgumentException("Clearing date cannot be null");
        }
       return fileClearing;
    }
}
