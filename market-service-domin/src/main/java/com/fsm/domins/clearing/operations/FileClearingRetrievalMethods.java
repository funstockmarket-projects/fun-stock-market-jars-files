package com.fsm.domins.clearing.operations;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "fileClearingRetrievalMethods")
public sealed interface FileClearingRetrievalMethods permits FunMarketFileClearingRetrievals {

    FileClearingBO findByFileClearingUuid(String fileClearingUuid);

    FileClearingBO findByFileUuid(String fileUuid);

    List<FileClearingBO> findAll();

    List<String> getFileNamesByValidationStatus(String status);
}




