package funMarketFileDetailsTest.fileClearingTest;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.ErrorCodesBO;
import com.fsm.domainsMapping.constantsBO.MarketEventsBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import com.fsm.domins.stockDetails.operations.FunMarketStockFileDetailsRetrievalMethods;
import com.fsm.domins.stockDetails.operations.FunMarketStockFileDetailsRetrievals;
import funMarketClearing.Exception.FileErrorContextException;
import funMarketClearing.clearingEngine.validation.FileModification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("FileModification Validation Tests")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FileModificationTest {

    @Mock
    private FunMarketStockFileDetailsRetrievals funMarketStockFileDetailsRetrievals;

//    @Mock
//    private FunMarketStockFileDetailsRetrievalMethods funMarketStockFileDetailsRetrievalMethods;

    @InjectMocks
    private FileModification fileModification;

    @BeforeEach
    public void setUp() {

        this.funMarketStockFileDetailsRetrievals = mock(FunMarketStockFileDetailsRetrievals.class);

//        this.funMarketStockFileDetailsRetrievalMethods = funMarketStockFileDetailsRetrievals;
        this.fileModification = new FileModification();
    }

    @Test
    @DisplayName("Should throw FileErrorContextException when FileMetadataBO is null")
    public void testProcessWithNullFileMetadata() {
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            fileModification.process(null);
        });
        assertEquals(ErrorCodesBO.ERR_1000.getCode(), exception.getCode());
    }

    @Test
    @DisplayName("Should not validate when record status is ADDED")
    public void testProcessWithAddRecordStatus() {
        FileMetadataBO fileMetadata = new FileMetadataBO();
        fileMetadata.setRecordStatusBO(RecordStatusBO.ADDED);
        assertDoesNotThrow(() -> fileModification.process(fileMetadata));
    }
}








