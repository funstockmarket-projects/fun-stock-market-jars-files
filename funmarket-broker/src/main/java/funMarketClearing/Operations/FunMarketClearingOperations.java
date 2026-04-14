package funMarketClearing.Operations;

import com.fsm.domainsMapping.businessObject.clearing.FileClearingBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import org.springframework.stereotype.Component;

@Component(value = "funMarketClearingOperations")
public interface FunMarketClearingOperations {
    public FileClearingBO saveClearing(String fileUuid, String fileName, String validationStatus, String errorCode, String errorMessage, String placeOFModification, RecordStatusBO recordStatus);
}