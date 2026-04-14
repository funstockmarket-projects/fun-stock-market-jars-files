package com.fsm.domins.information.operations;

import org.springframework.stereotype.Component;

@Component(value = "funMarketFileInformationRemoveMethods")
public sealed interface FunMarketFileInformationRemoveMethods permits FunMarketDeleteFileInformation {

    void removeFileInformationByUuid(String fileInformationUuid);
}
