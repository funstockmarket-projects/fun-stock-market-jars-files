package funMarketFileDetailsTest.fileInformationProcessingEngTest;

import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import funMarketExceptions.FunMarketException;
import funMarketFileInformation.service.FileInformationProcessingEng;
import funMarketFileInformation.service.dataFeeding.BuildNewFileInformation;
import funMarketFileInformation.service.dataFeeding.HandleModifiedFile;
import funMarketFileInformation.service.dataFeeding.ProcessedFileInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FileInformationProcessingEng Tests")
public class FileInformationProcessingEngTest {

    @Mock
    private BuildNewFileInformation buildNewFileInformation;

    @Mock
    private HandleModifiedFile handleModifiedFile;

    @Mock
    private ProcessedFileInformation processedFileInformation;

    private FileInformationProcessingEng subject;

    @BeforeEach
    public void setUp() {
        subject = new FileInformationProcessingEng(buildNewFileInformation, handleModifiedFile, 
                null, processedFileInformation);
    }

    @Test
    @DisplayName("Should throw FunMarketException when metadata list is null")
    void preProcessFileInformationMetaDataWithNullList() {
        FunMarketException exception = assertThrows(FunMarketException.class,
                () -> subject.preProcessFileInformationMetaData(null));

        assertTrue(exception.getMessage().contains("cannot be null or empty"));
    }

    @Test
    @DisplayName("Should throw FunMarketException when metadata list is empty")
    void preProcessFileInformationMetaDataWithEmptyList() {
        FunMarketException exception = assertThrows(FunMarketException.class,
                () -> subject.preProcessFileInformationMetaData(new ArrayList<>()));

        assertTrue(exception.getMessage().contains("cannot be null or empty"));
    }

    @Test
    @DisplayName("Should throw FunMarketException when no valid file information is found after preprocessing")
    void preProcessFileInformationMetaDataWithAllInvalidEntries() {
        List<Map<String, Object>> gitMetaData = new ArrayList<>();
        gitMetaData.add(getInvalidMetaData());

        FunMarketException exception = assertThrows(FunMarketException.class,
                () -> subject.preProcessFileInformationMetaData(gitMetaData));

        assertTrue(exception.getMessage().contains("No valid file information found after preprocessing"));
    }

    @Test
    @DisplayName("Should successfully preprocess valid metadata list and return file information")
    void preProcessFileInformationMetaDataWithValidEntries() {
        FileInformationBO expectedFileInfo = new FileInformationBO();
        expectedFileInfo.setFileName("testFile");

        List<Map<String, Object>> gitMetaData = new ArrayList<>();
        gitMetaData.add(getValidMetaData());

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedFileInfo);

        List<FileInformationBO> result = subject.preProcessFileInformationMetaData(gitMetaData);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testFile", result.getFirst().getFileName());
    }

    @Test
    @DisplayName("Should preprocess multiple metadata entries and return all valid file information")
    void preProcessFileInformationMetaDataWithMultipleValidEntries() {
        FileInformationBO fileInfo1 = new FileInformationBO();
        fileInfo1.setFileName("file1");
        FileInformationBO fileInfo2 = new FileInformationBO();
        fileInfo2.setFileName("file2");

        List<Map<String, Object>> gitMetaData = new ArrayList<>();
        gitMetaData.add(getValidMetaDataWithFileName("testFile1.csv"));
        gitMetaData.add(getValidMetaDataWithFileName("testFile2.csv"));

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(fileInfo1)
                .thenReturn(fileInfo2);

        List<FileInformationBO> result = subject.preProcessFileInformationMetaData(gitMetaData);

        assertEquals(2, result.size());
        verify(buildNewFileInformation, times(2)).processNewFileInformation(any());
    }

    @Test
    @DisplayName("Should skip invalid entries and return only valid file information")
    void preProcessFileInformationMetaDataWithMixedValidAndInvalidEntries() {
        FileInformationBO validFileInfo = new FileInformationBO();
        validFileInfo.setFileName("validFile");

        List<Map<String, Object>> gitMetaData = new ArrayList<>();
        gitMetaData.add(getInvalidMetaData());
        gitMetaData.add(getValidMetaData());
        gitMetaData.add(getInvalidMetaData());

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(validFileInfo);

        List<FileInformationBO> result = subject.preProcessFileInformationMetaData(gitMetaData);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should throw FunMarketException when metadata is null")
    void processFileInformationEngWithNullMetadata() {
        FunMarketException exception = assertThrows(FunMarketException.class,
                () -> subject.processFileInformationEng(null));

        assertTrue(exception.getMessage().contains("cannot be null or empty"));
    }

    @Test
    @DisplayName("Should throw FunMarketException when metadata is empty")
    void processFileInformationEngWithEmptyMetadata() {
        FunMarketException exception = assertThrows(FunMarketException.class,
                () -> subject.processFileInformationEng(new HashMap<>()));

        assertTrue(exception.getMessage().contains("cannot be null or empty"));
    }

    @Test
    @DisplayName("Should use default fileName when fileName key is missing")
    void processFileInformationEngWithMissingFileName() {
        FileInformationBO expectedResult = new FileInformationBO();

        Map<String, Object> metaData = new HashMap<>();
        metaData.put("status", "added");
        metaData.put("uploadTime", LocalDateTime.now().toString());

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedResult);

        FileInformationBO result = subject.processFileInformationEng(metaData);

        assertNotNull(result);
        verify(buildNewFileInformation, times(1)).processNewFileInformation(any());
    }

    @Test
    @DisplayName("Should throw FunMarketException when fileName is blank")
    void processFileInformationEngWithBlankFileName() {
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("fileName", "   ");
        metaData.put("status", "added");
        metaData.put("uploadTime", LocalDateTime.now().toString());

        FunMarketException exception = assertThrows(FunMarketException.class,
                () -> subject.processFileInformationEng(metaData));

        assertTrue(exception.getMessage().contains("File name cannot be null or blank"));
    }

    @Test
    @DisplayName("Should process added file status and delegate to buildNewFileInformation")
    void processFileInformationEngWithAddedStatus() {
        FileInformationBO expectedResult = new FileInformationBO();
        expectedResult.setFileName("newFile");

        Map<String, Object> metaData = getValidMetaData();
        metaData.put("status", "ADDED");

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedResult);

        FileInformationBO result = subject.processFileInformationEng(metaData);

        assertNotNull(result);
        assertEquals("newFile", result.getFileName());
        verify(buildNewFileInformation, times(1)).processNewFileInformation(any());
        verify(handleModifiedFile, never()).processModificationFileInformation(any());
    }

    @Test
    @DisplayName("Should process modified file status and delegate to handleModifiedFile")
    void processFileInformationEngWithModifiedStatus() {
        FileInformationBO expectedResult = new FileInformationBO();
        expectedResult.setFileName("modifiedFile");

        Map<String, Object> metaData = getValidMetaData();
        metaData.put("status", "MODIFIED");

        when(handleModifiedFile.processModificationFileInformation(any()))
                .thenReturn(expectedResult);

        FileInformationBO result = subject.processFileInformationEng(metaData);

        assertNotNull(result);
        assertEquals("modifiedFile", result.getFileName());
        verify(handleModifiedFile, times(1)).processModificationFileInformation(any());
        verify(buildNewFileInformation, never()).processNewFileInformation(any());
    }

    @Test
    @DisplayName("Should process removed file status and delegate to handleModifiedFile")
    void processFileInformationEngWithRemovedStatus() {
        FileInformationBO expectedResult = new FileInformationBO();
        expectedResult.setFileName("removedFile");

        Map<String, Object> metaData = getValidMetaData();
        metaData.put("status", "REMOVED");

        when(handleModifiedFile.handleRemovedFile(any()))
                .thenReturn(expectedResult);

        FileInformationBO result = subject.processFileInformationEng(metaData);

        assertNotNull(result);
        assertEquals("removedFile", result.getFileName());
        verify(handleModifiedFile, times(1)).handleRemovedFile(any());
        verify(buildNewFileInformation, never()).processNewFileInformation(any());
    }

    @Test
    @DisplayName("Should throw FunMarketException for unknown file status")
    void processFileInformationEngWithUnknownStatus() {
        Map<String, Object> metaData = getValidMetaData();
        metaData.put("status", "unknown_status");

        FunMarketException exception = assertThrows(FunMarketException.class,
                () -> subject.processFileInformationEng(metaData));

        assertTrue(exception.getMessage().contains("Unknown file status in GitHub metadata"));
    }

    @Test
    @DisplayName("Should handle file status in case-insensitive manner")
    void processFileInformationEngWithMixedCaseStatus() {
        FileInformationBO expectedResult = new FileInformationBO();
        expectedResult.setFileName("testFile");

        Map<String, Object> metaData = getValidMetaData();
        metaData.put("status", "AdDeD");

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedResult);

        FileInformationBO result = subject.processFileInformationEng(metaData);

        assertNotNull(result);
        verify(buildNewFileInformation, times(1)).processNewFileInformation(any());
    }

    @Test
    @DisplayName("Should process file with working day as Monday")
    void processFileInformationEngOnWorkingDay() {
        Map<String, Object> metaData = getValidMetaData();
        metaData.put("status", "added");
        metaData.put("uploadTime", getMonday().toString());

        FileInformationBO expectedResult = new FileInformationBO();
        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedResult);

        FileInformationBO result = subject.processFileInformationEng(metaData);

        assertNotNull(result);
        verify(buildNewFileInformation, times(1)).processNewFileInformation(any());
    }

    @Test
    @DisplayName("Should process file with non-working day as Saturday")
    void processFileInformationEngOnNonWorkingDay() {
        Map<String, Object> metaData = getValidMetaData();
        metaData.put("status", "added");
        metaData.put("uploadTime", getSaturday().toString());

        FileInformationBO expectedResult = new FileInformationBO();
        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedResult);

        FileInformationBO result = subject.processFileInformationEng(metaData);

        assertNotNull(result);
        verify(buildNewFileInformation, times(1)).processNewFileInformation(any());
    }

    @Test
    @DisplayName("Should remove dots from map keys when preprocessing metadata")
    void preProcessFileInformationMetaDataRemovesDots() {
        FileInformationBO expectedFileInfo = new FileInformationBO();
        expectedFileInfo.setFileName("testFile");

        Map<String, Object> metaData = new HashMap<>();
        metaData.put("fileName", "testFile.csv");
        metaData.put("status", "added");
        metaData.put("uploadTime", LocalDateTime.now().toString());
        metaData.put("branch.name", "main.branch");

        List<Map<String, Object>> gitMetaData = new ArrayList<>();
        gitMetaData.add(metaData);

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedFileInfo);

        List<FileInformationBO> result = subject.preProcessFileInformationMetaData(gitMetaData);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should handle metadata with multiple entries containing dots in string values")
    void preProcessFileInformationMetaDataWithDotsInMultipleEntries() {
        FileInformationBO fileInfo1 = new FileInformationBO();
        fileInfo1.setFileName("file1");
        FileInformationBO fileInfo2 = new FileInformationBO();
        fileInfo2.setFileName("file2");

        List<Map<String, Object>> gitMetaData = new ArrayList<>();
        Map<String, Object> meta1 = new HashMap<>();
        meta1.put("fileName", "file1.csv");
        meta1.put("status", "added");
        meta1.put("uploadTime", LocalDateTime.now().toString());
        meta1.put("author.email", "test@example.com");
        gitMetaData.add(meta1);

        Map<String, Object> meta2 = new HashMap<>();
        meta2.put("fileName", "file2.csv");
        meta2.put("status", "modified");
        meta2.put("uploadTime", LocalDateTime.now().toString());
        meta2.put("author.name", "John.Doe");
        gitMetaData.add(meta2);

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(fileInfo1);
        when(handleModifiedFile.processModificationFileInformation(any()))
                .thenReturn(fileInfo2);

        List<FileInformationBO> result = subject.preProcessFileInformationMetaData(gitMetaData);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should return FileInformationBO when valid metadata is processed")
    void processFileInformationEngReturnsValidFileInformation() {
        FileInformationBO expectedResult = new FileInformationBO();
        expectedResult.setFileInformationUuid("test-uuid");
        expectedResult.setFileName("validFile");

        Map<String, Object> metaData = getValidMetaData();
        metaData.put("status", "added");

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedResult);

        FileInformationBO result = subject.processFileInformationEng(metaData);

        assertNotNull(result);
        assertEquals("test-uuid", result.getFileInformationUuid());
        assertEquals("validFile", result.getFileName());
    }

    @Test
    @DisplayName("Should handle null metadata map in removeDotsFromMapKeys")
    void removeDotsFromMapKeysWithNullMap() {
        FileInformationBO expectedResult = new FileInformationBO();

        Map<String, Object> metaData = new HashMap<>();
        metaData.put("fileName", "testFile.csv");
        metaData.put("status", "added");
        metaData.put("uploadTime", LocalDateTime.now().toString());

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedResult);

        FileInformationBO result = subject.processFileInformationEng(metaData);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Should handle non-string values in metadata when removing dots")
    void removeDotsFromMapKeysWithNonStringValues() {
        FileInformationBO fileInfo = new FileInformationBO();
        fileInfo.setFileName("testFile");

        List<Map<String, Object>> gitMetaData = new ArrayList<>();
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("fileName", "testFile.csv");
        metaData.put("status", "added");
        metaData.put("uploadTime", LocalDateTime.now().toString());
        metaData.put("lineNumber", 42);
        metaData.put("timestamp", 1234567890L);
        metaData.put("active", true);
        gitMetaData.add(metaData);

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(fileInfo);

        List<FileInformationBO> result = subject.preProcessFileInformationMetaData(gitMetaData);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should catch exception when processing invalid metadata entry in list")
    void preProcessFileInformationMetaDataCatchesExceptionForInvalidEntry() {
        List<Map<String, Object>> gitMetaData = new ArrayList<>();
        Map<String, Object> validMetaData = getValidMetaData();
        gitMetaData.add(validMetaData);

        FileInformationBO expectedResult = new FileInformationBO();
        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedResult);

        List<FileInformationBO> result = subject.preProcessFileInformationMetaData(gitMetaData);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should process file with all valid metadata including optional fields")
    void processFileInformationEngWithCompleteMetadata() {
        FileInformationBO expectedResult = new FileInformationBO();
        expectedResult.setFileName("complexFile");

        Map<String, Object> metaData = new HashMap<>();
        metaData.put("fileName", "complexFile.csv");
        metaData.put("status", "added");
        metaData.put("uploadTime", LocalDateTime.now().toString());
        metaData.put("branch", "main");
        metaData.put("author", "testAuthor");
        metaData.put("message", "Test commit message");
        metaData.put("commitId", "abc123def456");

        when(buildNewFileInformation.processNewFileInformation(any()))
                .thenReturn(expectedResult);

        FileInformationBO result = subject.processFileInformationEng(metaData);

        assertNotNull(result);
        assertEquals("complexFile", result.getFileName());
    }

    @Test
    @DisplayName("Should handle empty status string in metadata")
    void processFileInformationEngWithEmptyStatus() {
        Map<String, Object> metaData = getValidMetaData();
        metaData.put("status", "");

        FunMarketException exception = assertThrows(FunMarketException.class,
                () -> subject.processFileInformationEng(metaData));

        assertTrue(exception.getMessage().contains("Unknown file status in GitHub metadata"));
    }


    private Map<String, Object> getValidMetaData() {
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("fileName", "testFile.csv");
        metaData.put("status", "added");
        metaData.put("uploadTime", LocalDateTime.now().toString());
        return metaData;
    }

    private Map<String, Object> getValidMetaDataWithFileName(String fileName) {
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("fileName", fileName);
        metaData.put("status", "added");
        metaData.put("uploadTime", LocalDateTime.now().toString());
        return metaData;
    }

    private Map<String, Object> getInvalidMetaData() {
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("fileName", "");
        metaData.put("status", "added");
        metaData.put("uploadTime", LocalDateTime.now().toString());
        return metaData;
    }

    private LocalDateTime getMonday() {
        LocalDateTime now = LocalDateTime.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        int daysUntilMonday = (8 - dayOfWeek) % 7;
        if (daysUntilMonday == 0) {
            daysUntilMonday = 7;
        }
        return now.plusDays(daysUntilMonday);
    }

    private LocalDateTime getSaturday() {
        LocalDateTime now = LocalDateTime.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        int daysUntilSaturday = (6 - dayOfWeek + 7) % 7;
        if (daysUntilSaturday == 0) {
            daysUntilSaturday = 7;
        }
        return now.plusDays(daysUntilSaturday);
    }
}






