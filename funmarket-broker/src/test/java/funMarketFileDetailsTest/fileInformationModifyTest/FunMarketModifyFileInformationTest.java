package funMarketFileDetailsTest.fileInformationModifyTest;

import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domainsMapping.constantsBO.RecordStatusBO;
import funMarketExceptions.FunMarketException;
import funMarketFileInformation.operations.FunMarketModifyFileInformation;
import funMarketFileInformation.operations.FunMarketSaveFileInformationBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FunMarketModifyFileInformation Tests")
public class FunMarketModifyFileInformationTest {

    @Mock
    private FunMarketSaveFileInformationBO fileInformationService;

    private FunMarketModifyFileInformation subject;

    @BeforeEach
    public void setUp() {
        subject = new FunMarketModifyFileInformation(fileInformationService);
    }

    @Test
    @DisplayName("Should throw FunMarketException when FileInformationBO is null")
    void modifyFileInformationIsNull() {
        FunMarketException exception = assertThrows(FunMarketException.class, 
            () -> subject.modifyFileInformationMetaData(null));

        assertTrue(exception.getMessage().contains("Cannot do modification until conditions match"));
        verify(fileInformationService, never()).saveFileInformationMetaData(any());
    }

    @Test
    @DisplayName("Should throw FunMarketException when RecordStatusBO is null")
    void recordStatusIsNull() {
        FileInformationBO fileInfo = getValidFileInfo();
        fileInfo.setRecordStatusBO(null);

        FunMarketException exception = assertThrows(FunMarketException.class,
            () -> subject.modifyFileInformationMetaData(fileInfo));

        assertTrue(exception.getMessage().contains("Cannot do modification until conditions match"));
        verify(fileInformationService, never()).saveFileInformationMetaData(fileInfo);
    }

    @Test
    @DisplayName("Should throw FunMarketException when RecordStatusBO is not MODIFIED")
    void recordStatusIsNotModified() {
        FileInformationBO fileInfo = getValidFileInfo();
        fileInfo.setRecordStatusBO(RecordStatusBO.ADDED);

        FunMarketException exception = assertThrows(FunMarketException.class,
            () -> subject.modifyFileInformationMetaData(fileInfo));

        assertTrue(exception.getMessage().contains("Cannot do modification until conditions match"));
        verify(fileInformationService, never()).saveFileInformationMetaData(fileInfo);
    }

    @Test
    @DisplayName("Should throw FunMarketException when GitHubFileStatus is null")
    void githubFileStatusIsNull() {
        FileInformationBO fileInfo = getValidFileInfo();
        fileInfo.setGitHubFileStatus(null);

        FunMarketException exception = assertThrows(FunMarketException.class,
            () -> subject.modifyFileInformationMetaData(fileInfo));

        assertTrue(exception.getMessage().contains("Cannot do modification until conditions match"));
        verify(fileInformationService, never()).saveFileInformationMetaData(fileInfo);
    }

    @Test
    @DisplayName("Should throw FunMarketException when GitHubFileStatus is not MODIFIED")
    void githubFileStatusIsNotModified() {
        FileInformationBO fileInfo = getValidFileInfo();
        fileInfo.setGitHubFileStatus(RecordStatusBO.ADDED);

        FunMarketException exception = assertThrows(FunMarketException.class,
            () -> subject.modifyFileInformationMetaData(fileInfo));

        assertTrue(exception.getMessage().contains("Cannot do modification until conditions match"));
        verify(fileInformationService, never()).saveFileInformationMetaData(fileInfo);
    }

    @Test
    @DisplayName("Should successfully modify FileInformationBO when all validations pass")
    void modifyValidFileInformation() {
        FileInformationBO fileInfo = getValidFileInfo();
        FileInformationBO expectedResult = getValidFileInfo();

        when(fileInformationService.saveFileInformationMetaData(fileInfo)).thenReturn(expectedResult);

        FileInformationBO result = subject.modifyFileInformationMetaData(fileInfo);

        assertNotNull(result);
        assertEquals(expectedResult.getFileInformationUuid(), result.getFileInformationUuid());
        assertEquals(expectedResult.getFileName(), result.getFileName());
        verify(fileInformationService, times(1)).saveFileInformationMetaData(fileInfo);
    }

    private FileInformationBO getValidFileInfo() {
        FileInformationBO fileInformationBO = new FileInformationBO();
        fileInformationBO.setFileInformationUuid("123e4567-e89b-12d3-a456-426614174000");
        fileInformationBO.setFileName("testFile.csv");
        fileInformationBO.setFileStockDetailsUuid("123e4567-e89b-12d3-a456-426614174001");
        fileInformationBO.setGitHubFileStatus(RecordStatusBO.MODIFIED);
        fileInformationBO.setRecordStatusBO(RecordStatusBO.MODIFIED);
        return fileInformationBO;
    }
}


