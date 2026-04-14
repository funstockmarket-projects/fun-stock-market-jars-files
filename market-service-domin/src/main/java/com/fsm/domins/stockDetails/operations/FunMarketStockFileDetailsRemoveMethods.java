package com.fsm.domins.stockDetails.operations;

import org.springframework.stereotype.Component;

@Component(value = "funMarketStockFileDetailsRemoveMethods")
public sealed interface FunMarketStockFileDetailsRemoveMethods permits FunMarketDeleteStockFileDetails {

    void removeStockFileDetailsByUUID(String fileUUID);
}
