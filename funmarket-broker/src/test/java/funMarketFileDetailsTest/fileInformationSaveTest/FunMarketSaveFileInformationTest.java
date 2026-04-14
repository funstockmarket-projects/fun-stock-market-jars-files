package funMarketFileDetailsTest.fileInformationSaveTest;

import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import com.fsm.domins.information.operations.FunMarketSaveFileInformation;
import funMarketExceptions.FunMarketException;
import funMarketFileInformation.operations.FileInformationOperationsManagerBO;
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
@DisplayName("FunMarketSaveFileInformation Tests")
public class FunMarketSaveFileInformationTest {

    @Mock
    private FunMarketSaveFileInformation funMarketSaveFileInformation;
    
    private FileInformationOperationsManagerBO subject;

    @BeforeEach
    public void setUp(){
        subject = new FunMarketSaveFileInformationBO(funMarketSaveFileInformation);
    }

    @Test
    @DisplayName("Should throw FunMarketException when FileInformationBO is null")
    void fileInformationIsNull(){
        FunMarketException funMarketException = assertThrows(FunMarketException.class, ()->subject.saveFileInformationMetaData(null));

        assertTrue(funMarketException.getMessage().contains("FileInformationBO is not valid"));
        verify(funMarketSaveFileInformation, never()).saveFileInformation(any());
    }

    @Test
    @DisplayName("Should throw FunMarketException when fileInformationUuid is null")
    void fileInformationUuidIsNull(){
        FileInformationBO fileInfo = getFileInfo();
        fileInfo.setFileInformationUuid(null);
        
        FunMarketException funMarketException = assertThrows(FunMarketException.class, ()->subject.saveFileInformationMetaData(fileInfo));

        assertTrue(funMarketException.getMessage().contains("FileInformationBO is not valid"));
        verify(funMarketSaveFileInformation, never()).saveFileInformation(fileInfo);
    }

    @Test
    @DisplayName("Should throw FunMarketException when fileInformationUuid is empty")
    void fileInformationUuidIsEmpty(){
        FileInformationBO fileInfo = getFileInfo();
        fileInfo.setFileInformationUuid("");
        
        FunMarketException funMarketException = assertThrows(FunMarketException.class, ()->subject.saveFileInformationMetaData(fileInfo));

        assertTrue(funMarketException.getMessage().contains("FileInformationBO is not valid"));
        verify(funMarketSaveFileInformation, never()).saveFileInformation(fileInfo);
    }

    @Test
    @DisplayName("Should throw FunMarketException when fileName is null")
    void fileNameIsNull(){
        FileInformationBO fileInfo = getFileInfo();
        fileInfo.setFileName(null);
        
        FunMarketException funMarketException = assertThrows(FunMarketException.class, ()->subject.saveFileInformationMetaData(fileInfo));

        assertTrue(funMarketException.getMessage().contains("FileInformationBO is not valid"));
        verify(funMarketSaveFileInformation, never()).saveFileInformation(fileInfo);
    }

    @Test
    @DisplayName("Should throw FunMarketException when fileName is empty")
    void fileNameIsEmpty(){
        FileInformationBO fileInfo = getFileInfo();
        fileInfo.setFileName("");
        
        FunMarketException funMarketException = assertThrows(FunMarketException.class, ()->subject.saveFileInformationMetaData(fileInfo));

        assertTrue(funMarketException.getMessage().contains("FileInformationBO is not valid"));
        verify(funMarketSaveFileInformation, never()).saveFileInformation(fileInfo);
    }

    @Test
    @DisplayName("Should throw FunMarketException when fileStockDetailsUuid is null")
    void fileStockDetailsUuidIsNull(){
        FileInformationBO fileInfo = getFileInfo();
        fileInfo.setFileStockDetailsUuid(null);
        
        FunMarketException funMarketException = assertThrows(FunMarketException.class, ()->subject.saveFileInformationMetaData(fileInfo));

        assertTrue(funMarketException.getMessage().contains("FileInformationBO is not valid"));
        verify(funMarketSaveFileInformation, never()).saveFileInformation(fileInfo);
    }

    @Test
    @DisplayName("Should throw FunMarketException when fileStockDetailsUuid is empty")
    void fileStockDetailsUuidIsEmpty(){
        FileInformationBO fileInfo = getFileInfo();
        fileInfo.setFileStockDetailsUuid("");
        
        FunMarketException funMarketException = assertThrows(FunMarketException.class, ()->subject.saveFileInformationMetaData(fileInfo));

        assertTrue(funMarketException.getMessage().contains("FileInformationBO is not valid"));
        verify(funMarketSaveFileInformation, never()).saveFileInformation(fileInfo);
    }

    @Test
    @DisplayName("Should successfully save valid FileInformationBO")
    void saveValidFileInformation(){
        FileInformationBO fileInfo = getFileInfo();
        
        FileInformationBO result = subject.saveFileInformationMetaData(fileInfo);
        
        assertNotNull(result);
        assertEquals(fileInfo.getFileInformationUuid(), result.getFileInformationUuid());
        assertEquals(fileInfo.getFileName(), result.getFileName());
        verify(funMarketSaveFileInformation, times(1)).saveFileInformation(fileInfo);
    }

    private FileInformationBO getFileInfo(){
        FileInformationBO fileInformationBO = new FileInformationBO();
        fileInformationBO.setFileInformationUuid("123e4567-e89b-12d3-a456-426614174000");
        fileInformationBO.setFileName("testFile.csv");
        fileInformationBO.setFileStockDetailsUuid("123e4567-e89b-12d3-a456-426614174001");
        return fileInformationBO;
    }
}
