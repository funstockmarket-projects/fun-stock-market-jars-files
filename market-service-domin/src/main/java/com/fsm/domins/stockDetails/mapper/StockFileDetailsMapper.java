package com.fsm.domins.stockDetails.mapper;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.MarketEventsBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domins.stockDetails.models.FileMetadata;
import com.fsm.domins.globalenums.MarketEvents;
import com.fsm.domins.globalenums.RecordStatus;;

public class StockFileDetailsMapper {

    public static FileMetadata bOToStockFileDetails(FileMetadataBO bo) {
        return new FileMetadata(
                bo.getFileUUID(),
                bo.getFileName(),
                bo.getFolderName(),
                bo.getFileType(),
                bo.getFileSize(),
                bo.getNumberOfRecords(),
                bo.getUri(),
                MarketEvents.valueOf(bo.getEventNameBO().getEventName()),
                bo.getFileUploadDate(),
                bo.getFileModifiedDate(),
                bo.getFileData(),
                RecordStatus.valueOf(bo.getRecordStatusBO().getValue()),
                bo.getValidationStatus(),
                bo.getValidationMessage() != null ? bo.getValidationMessage() : " ",
                bo.getFileInformationUUID()!= null ? bo.getFileInformationUUID() : " ",
                RecordStatus.valueOf(bo.getFileInformationRecordStatus().getValue() != null ? bo.getFileInformationRecordStatus().getValue() : RecordStatus.UNKNOWN.getValue())
        );
    }

    public static FileMetadataBO stockFileDetailsToBO(FileMetadata sfd) {
        if (sfd == null) {
            throw new IllegalArgumentException("FileMetadata cannot be null");
        }
        if (sfd.eventName() == null) {
            throw new IllegalArgumentException("EventName cannot be null");
        }
        if (sfd.recordStatus() == null) {
            throw new IllegalArgumentException("RecordStatus cannot be null");
        }
        
        FileMetadataBO bo = new FileMetadataBO();
        bo.setFileUUID(sfd.fileUUID());
        bo.setFileName(sfd.fileName());
        bo.setFolderName(sfd.folderName());
        bo.setFileType(sfd.fileType());
        bo.setFileSize(sfd.fileSize());
        bo.setNumberOfRecords(sfd.numberOfRecords());
        bo.setUri(sfd.uri());
        bo.setEventNameBO(MarketEventsBO.valueOf(sfd.eventName().getEventName()));
        bo.setFileUploadDate(sfd.fileUploadDate());
        bo.setFileModifiedDate(sfd.fileModifiedDate());
        bo.setFileData(sfd.fileData());
        bo.setRecordStatusBO(RecordStatusBO.valueOf(sfd.recordStatus().getValue()));
        bo.setValidationStatus(sfd.validationStatus() != null ? sfd.validationStatus() : " ");
        bo.setValidationMessage(sfd.validationMessage() != null ? sfd.validationMessage() : " ");
        bo.setFileInformationUUID(sfd.fileInformationUUID() != null ? sfd.fileInformationUUID() : " ");
        bo.setFileInformationRecordStatus(RecordStatusBO.valueOf(sfd.fileInformationRecordStatus().getValue()!= null ? sfd.fileInformationRecordStatus().getValue() : RecordStatus.UNKNOWN.getValue()));
        return bo;
    }
}
