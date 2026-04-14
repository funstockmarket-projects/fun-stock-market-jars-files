package funMarketFileDetailsTest.fileClearingTest;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import funMarketClearing.Exception.FileErrorContextException;
import funMarketClearing.clearingEngine.validation.CheckFileNamePattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CheckFilePatternTest {

    private CheckFileNamePattern subject;

    @BeforeEach
    public void setUp(){
        subject = new CheckFileNamePattern();
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
    public void checkForValidFileNames(){
        List.of("01 2024 1_day January", "02 2023 2_day February", "10 2022 31_day March", "555 2021 30_day April",
                        "555 2021 30_day apr", "555 2021 30_day Apr", "02 2025 1_day Dec")
                .forEach(fileName -> {
                    try {
                        FileMetadataBO metadata = new FileMetadataBO(); metadata.setFileName(fileName);
                        assertDoesNotThrow(()->{
                            subject.process(metadata);
                        });
                    } catch (FileErrorContextException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    public void checkForInvalidFileNames(){
        List.of("2024 01_day January", "0 2024 1_day January",                                                                        //serial
                        "01 224 01_day January",  "01  1_day January",                                                                          //year
                        "01 2024 0_day January", "01 2024 33_day January",  "01 2024 _day January", "01 2024 01_day January",                   //date
                        "02 2023 02_week February", "10 2022 31_year March",   "10 2022 31_ March",                                             //event name
                        "102022 31_day March", "10 202231_day March", "10 2022 31_dayMarch",  "10 2022 31_day",                                 //spaces
                        "10 2022 31_day March.csv"                                                                                              //File ext
                )
                .forEach(fileName -> {
                    FileMetadataBO metadata = new FileMetadataBO(); metadata.setFileName(fileName);
                    FileErrorContextException exception = assertThrows(FileErrorContextException.class, () -> {
                        subject.process(metadata);
                    });
                    assertEquals("1002", exception.getCode(), "Expected error code for invalid file name pattern");
                });
    }
}
