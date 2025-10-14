package Modules.CommonModels.pojo;

import Modules.CommonModels.enums.FileStatus;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class FileName {
    static {
        log.info("FileName POJO Initialized");
    }

    private String fileName;
    private FileStatus fileStatus;
}