package Modules.CommonModels.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ApiResponse<T> {

    private String message;
    private T data;
    private HttpStatus httpStatusCode;

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
