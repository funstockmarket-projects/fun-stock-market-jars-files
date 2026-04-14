package com.fsm.domins.clearing.mapper;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domins.clearing.models.FileClearing;
import com.fsm.domins.globalenums.RecordStatus;

public class FileClearingMapper {

    public static FileClearing bOToFileClearing(FileClearingBO fileClearingBO) {
        return new FileClearing(
                fileClearingBO.getFileClearingUuid(),
                fileClearingBO.getFileUuid(),
                fileClearingBO.getFileName(),
                fileClearingBO.getFileValidationStatus(),
                fileClearingBO.getClearingCode(),
                fileClearingBO.getClearingMessage(),
                fileClearingBO.getClearingDate(),
                fileClearingBO.getModifiedDate(),
                fileClearingBO.getPlaceOFModification(),
                RecordStatus.valueOf(fileClearingBO.getClearingRecordStatus().getValue())
        );
    }

    public static FileClearingBO FileClearingToBO(FileClearing fileClearing) {
        if (fileClearing == null) {
            throw new IllegalArgumentException("FileClearing cannot be null");
        }
        if (fileClearing.clearingRecordStatus() == null) {
            throw new IllegalArgumentException("ClearingRecordStatus cannot be null");
        }
        
        FileClearingBO fileClearingBO = new FileClearingBO();
        fileClearingBO.setFileClearingUuid(fileClearing.fileClearingUuid());
        fileClearingBO.setFileUuid(fileClearing.fileUuid());
        fileClearingBO.setFileName(fileClearing.fileName());
        fileClearingBO.setFileValidationStatus(fileClearing.fileValidationStatus());
        fileClearingBO.setClearingCode(fileClearing.clearingCode());
        fileClearingBO.setClearingMessage(fileClearing.clearingMessage());
        fileClearingBO.setClearingDate(fileClearing.clearingDate());
        fileClearingBO.setModifiedDate(fileClearing.modifiedDate());
        fileClearingBO.setPlaceOFModification(fileClearing.placeOFModification());
        fileClearingBO.setClearingRecordStatus(RecordStatusBO.valueOf(fileClearing.clearingRecordStatus().getValue()));
        return fileClearingBO;
    }
}






