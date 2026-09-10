package modeling.trade.Configuration;

import modeling.DTOMapping.DomainMapping;
import modeling.globalEnums.ProcessingStatus;
import modeling.globalEnums.YesOrNoStatusFlag;
import modeling.trade.DTO.ClearingDTO;

/**
 * ClearingDBOperations
 */
public class ClearingDBOperations {

    private final ClearingRepository clearingRepository;

    public ClearingDBOperations(ClearingRepository clearingRepository) {
        this.clearingRepository = clearingRepository;
    }

    public ClearingDTO findByCleaaringId(Long clearingId) {
        return clearingRepository.findByClearingId(clearingId)
                .map(entity -> DomainMapping.toDto(entity))
                .orElse(null);
    }

    public ClearingDTO findByUserId(Long userId) {
        return clearingRepository.findByUserId(userId)
                .stream()
                .map(entity -> DomainMapping.toDto(entity))
                .findFirst()
                .orElse(null);
    }

    public ClearingDTO findByClearingStatusCode(ProcessingStatus clearingStatusCode) {
        return clearingRepository.findByClearingStatusCode(clearingStatusCode)
                .stream()
                .map(entity -> DomainMapping.toDto(entity))
                .findFirst()
                .orElse(null);
    }

    public ClearingDTO findByIsRejectedTrade(YesOrNoStatusFlag isRejectedTrade) {
        return clearingRepository.findByIsRejectedTrade(isRejectedTrade)
                .stream()
                .map(entity -> DomainMapping.toDto(entity))
                .findFirst()
                .orElse(null);
    }
}
