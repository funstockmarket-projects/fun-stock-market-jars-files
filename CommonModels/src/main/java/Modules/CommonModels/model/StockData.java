package Modules.CommonModels.model;

import Modules.CommonModels.enums.StockStatus;
import lombok.Builder;

@Builder
public record StockData(
        String instrument,//1
        int quantity,//2
        double averageCost,//3
        double ltp,//4
        double currentValue,//5
        double profitOrLoss,//6
        double netChange,//7
        double dayChange,//8
        StockStatus stockStatus
) {
    public boolean validate() {

        if (this.instrument == null || this.instrument.trim().isEmpty()) {

            return true;
        }
        if (this.quantity < 0) {
            return true;
        }
        if (this.averageCost < 0) {
            return true;
        }
        if (this.ltp < 0) {
            return true;
        }
        if (this.currentValue < 0) {
            return true;
        }
        return false;
    }
}	
