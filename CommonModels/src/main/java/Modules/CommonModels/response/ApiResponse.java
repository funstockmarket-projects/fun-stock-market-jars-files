package Modules.CommonModels.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static Modules.CommonModels.enums.helperConstants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private String message;
    private T data;
    private HttpStatus httpStatusCode;

    public static final Map<String, String> apiURLs = Map.of(
            MONTHLY_PERFORMANCE,
            "http://localhost:9093/api/stocks/monthlyPerformance/holdings",
            FILES_UPDATED,
            "http://localhost:9093/api/stocks/market/file/fileNamesBasedOnStatus/updated",
            FILES_INCOMPLETE,
            "http://localhost:9093/api/stocks/market/file/fileNamesBasedOnStatus/incomplete",
            SAVE_EVENT,
            "http://localhost:9093/api/stocks/market/event/saveFile"

    );

    public static <T> ApiResponse<?> apiConnector(
            String url,
            HttpMethod httpMethod,
            HttpEntity<?> httpEntity,
            ParameterizedTypeReference<ApiResponse<T>> typeRef){

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
                url,
                httpMethod,
                httpEntity,
                typeRef
        );
        return response.getBody();
    }
}
