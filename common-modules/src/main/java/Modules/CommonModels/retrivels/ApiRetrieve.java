package Modules.CommonModels.retrivels;

import Modules.CommonModels.pojo.ConnectionURLs;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static Modules.CommonModels.enums.helperConstants.*;

@Slf4j
@Component
@Getter
public class ApiRetrieve {

    @Autowired
    private ConnectionURLs connectionURLs;

    @Bean
    public Map<String, String> apiURLs() {
        return Map.of(
                VALIDATED,
                connectionURLs.getValidationUrl() + VALIDATED,
                FILES_VALID,
                connectionURLs.getValidationUrl() + FILES_VALID,
                FILES_INVALID,
                connectionURLs.getValidationUrl() + FILES_INVALID
        );
    }

    public static String applicationPropertiesReader(String fileName, String key) {
        log.info("Reading application properties from file: {} for key: {}", fileName, key);
        Properties properties = new Properties();
        try (InputStream input = ApiRetrieve.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                log.info("Sorry, unable to find, {}", fileName);
            }
            properties.load(input);
            String value = properties.getProperty(key);
            if (value == null) {
                log.error("Key not found: {}", key);
            }
            return value;
        } catch (IOException ex) {
            log.error("IOException occurred: {}", ex.getMessage());
        }
        return null;
    }
}
