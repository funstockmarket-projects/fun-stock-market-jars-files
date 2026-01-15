package com.fsm.domins.clearing.service;

import com.fsm.domins.clearing.models.FileClearing;

public class FileClearingDominService {

    public FileClearing processFileClearing(FileClearing fileClearing) {

        if(fileClearing == null) {
            throw new IllegalArgumentException("FileClearing object cannot be null");
        }
        if(fileClearing.getFileUuid() == null || fileClearing.getFileUuid().isBlank()) {
            throw new IllegalArgumentException("File UUID cannot be null or blank");
        }
        if(fileClearing.getClearingCode() == null || fileClearing.getClearingCode().isBlank()) {
            throw new IllegalArgumentException("Clearing code cannot be null or blank");
        }
        String status = fileClearing.getFileValidationStatus();
        if(status == null || status.isBlank() || !status.equals("CLEARED") && !status.equals("REJECTED") && !status.equals("NOT_CLEARED")) {
            throw new IllegalArgumentException("File validation status cannot be null or blank");
        }
        if(fileClearing.getClearingMessage() == null || fileClearing.getClearingMessage().isBlank()) {
            throw new IllegalArgumentException("Clearing message cannot be null or blank");
        }
        if(fileClearing.getClearingDate() == null) {
            throw new IllegalArgumentException("Clearing date cannot be null");
        }
       return fileClearing;
    }
}
