package com.fsm.domins.information.operations;

import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier(value = "funMarketSaveFileInformationMethod")
public sealed interface FunMarketSaveFileInformationMethod permits FunMarketSaveFileInformation {

    FileInformationBO saveFileInformation(FileInformationBO fileInformationBO);
}
