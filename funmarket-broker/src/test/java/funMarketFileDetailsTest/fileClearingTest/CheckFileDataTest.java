package funMarketFileDetailsTest.fileClearingTest;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.MarketEventsBO;
import funMarketClearing.Exception.FileErrorContextException;
import funMarketClearing.clearingEngine.validation.CheckFileData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckFileDataTest {

    private CheckFileData subject;

    @BeforeEach
    public void setUp() {
        subject = new CheckFileData();
    }

    @Test
    public void checkFileDateWithNullObjTest() {

        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(null);
        });
        assertEquals("1000", exception.getCode());
        assertEquals("INVALID FILE", exception.getMessage());
    }

    @Test
    public void checkFileDateWithNullFileUUIDTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFileUUID(null);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3002", exception.getCode());
    }

    @Test
    public void checkFileDateWithEmptyFileUUIDTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFileUUID("   ");
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3002", exception.getCode());
    }

    @Test
    public void checkFileDateWithNullFileNameTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFileName(null);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("1001", exception.getCode());
    }

    @Test
    public void checkFileDateWithNullFolderNameTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFolderName(null);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3003", exception.getCode());
    }

    @Test
    public void checkFileDateWithNullFileTypeTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFileType(null);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3004", exception.getCode());
    }

    @Test
    public void checkFileDateWithNullURITest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setUri(null);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3007", exception.getCode());
    }

    @Test
    public void checkFileDateWithInvalidFileSizeTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFileSize(0L);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3005", exception.getCode());
    }

    @Test
    public void checkFileDateWithNullFileSizeTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFileSize(null);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3005", exception.getCode());
    }

    @Test
    public void checkFileDateWithInvalidRecordsCountTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setNumberOfRecords(0);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3006", exception.getCode());
    }

    @Test
    public void checkFileDateWithNullEventNameTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setEventNameBO(null);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3012", exception.getCode());
    }

    @Test
    public void checkFileDateWithNullUploadDateTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFileUploadDate(null);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3009", exception.getCode());
    }

    @Test
    public void checkFileDateWithNullModifiedDateTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFileModifiedDate(null);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3010", exception.getCode());
    }

    @Test
    public void checkFileDateWithNullFileDataTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFileData(null);
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3008", exception.getCode());
    }

    @Test
    public void checkFileDateWithEmptyFileDataTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        fileMetadata.setFileData(new ArrayList<>());
        
        FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
            subject.process(fileMetadata);
        });
        assertEquals("3008", exception.getCode());
    }

    @Test
    public void checkFileDateWithValidFileDataTest() {
        FileMetadataBO fileMetadata = createValidFileMetadata();
        // Should not throw exception for valid data
        try {
            subject.process(fileMetadata);
        } catch (FileErrorContextException e) {
            throw new AssertionError("Validation should not throw exception for valid data", e);
        }
    }

    private FileMetadataBO createValidFileMetadata() {
        FileMetadataBO fileMetadata = new FileMetadataBO();
        fileMetadata.setFileUUID("UUID-123");
        fileMetadata.setFileName("test-file.csv");
        fileMetadata.setFolderName("test-folder");
        fileMetadata.setFileType("CSV");
        fileMetadata.setUri("s3://bucket/test-file.csv");
        fileMetadata.setFileSize(1024L);
        fileMetadata.setNumberOfRecords(100);
        
        // Use the first available enum constant for MarketEventsBO
        MarketEventsBO[] events = MarketEventsBO.values();
        if (events.length > 0) {
            fileMetadata.setEventNameBO(events[0]);
        }
        
        fileMetadata.setFileUploadDate(LocalDateTime.now());
        fileMetadata.setFileModifiedDate(LocalDateTime.now());
        
        List<Map<String, Object>> fileData = new ArrayList<>();
        Map<String, Object> record = new HashMap<>();
        record.put("col1", "value1");
        fileData.add(record);
        fileMetadata.setFileData(fileData);
        
        return fileMetadata;
    }
}
