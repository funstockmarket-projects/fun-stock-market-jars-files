package funMarketClearing.clearingEngine.validation;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.ErrorCodesBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domins.stockDetails.operations.FunMarketStockFileDetailsRetrievalMethods;
import funMarketClearing.Exception.FileErrorContextException;
import funMarketClearing.clearingEngine.validation.fileOperations.FileNameToDateConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FileTimePeriod extends FileValidationAbstractService {

    private final Logger log = LoggerFactory.getLogger(FileTimePeriod.class);

    private final FunMarketStockFileDetailsRetrievalMethods stockFileDetailsRetrievalMethods;

    public FileTimePeriod(@Qualifier(value = "funMarketStockFileDetailsRetrievals") FunMarketStockFileDetailsRetrievalMethods stockFileDetailsRetrievalMethods) {
        this.stockFileDetailsRetrievalMethods = stockFileDetailsRetrievalMethods;
    }

    @Override
    public void process(FileMetadataBO stockFileDetailsBO) {
        if (stockFileDetailsBO == null) {
            log.error("Validation failed: FileMetadataBO is null");
            throw new FileErrorContextException(ErrorCodesBO.ERR_1000);
        }

        if (RecordStatusBO.ADDED.equals(stockFileDetailsBO.getRecordStatusBO())) {
            final String fileName = stockFileDetailsBO.getFileName();
            log.info("Validating time period for file: {}", fileName);

            String[] placeHolders = FileNameToDateConversion.getFileNamePlaceHolders(fileName);
            long year = Long.parseLong(placeHolders[1]);
            long presentYear = LocalDate.now().getYear();

            if (year < 2000 || year > presentYear) {
                log.error("Validation failed: Year {} in file {} is out of valid range (2000 - {})", year, fileName, presentYear);
                throw new FileErrorContextException("Invalid year in filename", ErrorCodesBO.ERR_501);
            }

            List<LocalDate> previousFileYearsDates = stockFileDetailsRetrievalMethods.findAll()
                    .stream()
                    .map(FileMetadataBO::getFileName)
                    .toList()
                    .stream()
                    .map(FileNameToDateConversion::getDate)
                    .toList();
            LocalDate presentFileDate = FileNameToDateConversion.getDate(fileName);
            log.info("Checking file date with previousFileDates,fileName: [ {} ] previousFileList size: [ {} ], Converted FileName to Date: [ {} ] ", fileName, previousFileYearsDates.size(), presentFileDate);

            if (previousFileYearsDates.contains(presentFileDate)) {
                log.error("Duplicate date detected! File [ {} ] refers to [ {} ], which was already processed from a previous file", fileName, presentFileDate);
                throw new FileErrorContextException("Duplicate date detected! File [ " + fileName + " ] refers to [ " + presentFileDate + " ], which was already processed from a previous file", ErrorCodesBO.ERR_502);
            }else{
                log.info("validation passed in the [ {} ]", FileTimePeriod.class.getSimpleName());
            }
        }
    }
}