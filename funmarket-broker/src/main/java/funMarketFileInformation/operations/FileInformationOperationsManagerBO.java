package funMarketFileInformation.operations;

import com.fsm.domainsMapping.businessObject.fileInformationBO.FileInformationBO;
import org.springframework.stereotype.Component;

@Component(value = "fileInformationOperationsManager")
public interface FileInformationOperationsManagerBO {
    FileInformationBO saveFileInformationMetaData(FileInformationBO fileInformationBo);
    FileInformationBO modifyFileInformationMetaData(FileInformationBO fileInformationBO);
}
