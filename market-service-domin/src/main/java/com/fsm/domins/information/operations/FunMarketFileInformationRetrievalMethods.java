package com.fsm.domins.information.operations;

import com.fsm.domins.information.models.FileInformation;
import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "funMarketFileInformationRetrievalMethods")
public sealed interface FunMarketFileInformationRetrievalMethods permits FunMarketFileInformationRetrievals {

    FileInformationBO findByFileInformationUuid(String fileInformationUuid);

    FileInformationBO findByFileName(String fileName);

    List<FileInformationBO> findAll();
}
