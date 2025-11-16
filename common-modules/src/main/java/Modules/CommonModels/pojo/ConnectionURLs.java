package Modules.CommonModels.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConnectionURLs {
    @Value("${fun.market.file.get.file.by.validation.status.uri}")
    private String validationUrl;

    @Bean
    public String getValidationUrl() {
        return validationUrl;
    }
}
