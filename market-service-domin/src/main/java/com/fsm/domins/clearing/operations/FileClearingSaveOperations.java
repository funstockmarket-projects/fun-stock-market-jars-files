package com.fsm.domins.clearing.operations;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domins.clearing.models.FileClearing;
import org.springframework.stereotype.Component;

@Component(value = "fileClearingSaveOperations")
public sealed interface FileClearingSaveOperations permits FunMarketSaveFileClearing {

    FileClearingBO save(FileClearingBO fileClearingBO);
}






