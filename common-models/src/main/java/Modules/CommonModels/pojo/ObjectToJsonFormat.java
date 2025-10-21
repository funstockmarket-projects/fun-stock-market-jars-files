package Modules.CommonModels.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ObjectToJsonFormat {

    private static final Logger log = LoggerFactory.getLogger(ObjectToJsonFormat.class);

    public static String toJsonFormat(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("Converting object to JSON format: ");
            String result= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            log.info("Conversion successful.");
            return result;
        } catch (JsonProcessingException e) {
            log.error("Error converting object to JSON format: {}", e.getMessage());
            return "{}";
        }
    }
}
