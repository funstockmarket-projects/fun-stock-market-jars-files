package Modules.CommonModels.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ObjectToJsonFormat {

    // Using SLF4J (Standard with Spring Boot)
    private static final Logger log = LoggerFactory.getLogger(ObjectToJsonFormat.class);

    /**
     * Converts a Java Object into a Pretty-Printed JSON String.
     * @param object The object to convert.
     * @return JSON String or empty JSON object on error.
     */
    public static String toJsonFormat(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("Converting object to JSON format...");
            String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            log.info("Conversion successful.");
            return result;
        } catch (JsonProcessingException e) {
            // Updated to use SLF4J curly-brace placeholder syntax
            log.error("Error converting object to JSON format: {}", e.getMessage());
            return "{}";
        }
    }
}
