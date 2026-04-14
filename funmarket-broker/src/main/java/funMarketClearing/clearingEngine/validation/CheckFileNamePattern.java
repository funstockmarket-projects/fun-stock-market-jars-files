package funMarketClearing.clearingEngine.validation;

import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.ErrorCodesBO;
import funMarketClearing.Exception.FileErrorContextException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CheckFileNamePattern extends FileValidationAbstractService {

    private final Logger log = LoggerFactory.getLogger(CheckFileNamePattern.class);

    // Regex breakdown:
    // ^                : Start of string
    // [a-zA-Z0-9]+     : Serial Number (Alphanumeric)
    // _                : Underscore separator
    // \\d{4}           : Year (4 digits)
    // _                : Underscore separator
    // \\d+             : Number (1 or more digits)
    // _                : Underscore separator
    // (week|month|day|year) : Event Name (Strictly one of these four)
    // _                : Underscore separator
    // [a-zA-Z]+        : Month (Letters)
    // $                : End of string
    private static final String FILE_NAME_REGEX = "^(?!0$)\\d{2,}\\s+\\d{4}\\s+([1-9]|[12]\\d|3[01])_day\\s+[a-zA-Z]+$";
    private static final Pattern PATTERN = Pattern.compile(FILE_NAME_REGEX, Pattern.CASE_INSENSITIVE);

    @Override
    public void process(FileMetadataBO stockFileDetailsBO) {
        if (stockFileDetailsBO == null) {
            throw new FileErrorContextException(ErrorCodesBO.ERR_1000);
        }
        final String fileName = stockFileDetailsBO.getFileName();
        log.info("Validating file name pattern for: {}", fileName);

        // 2. Execute Regex Pattern Validation
        Matcher matcher = PATTERN.matcher(fileName);
        if (!matcher.matches()) {
            log.error("Pattern mismatch: File name '{}' does not match required format [serial year number_event month]", fileName);
            throw new FileErrorContextException(ErrorCodesBO.ERR_1002);
        }

        log.info("File name pattern validation passed for: {}", fileName);
    }
}