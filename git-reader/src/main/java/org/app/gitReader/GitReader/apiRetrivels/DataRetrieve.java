package org.app.gitReader.GitReader.apiRetrivels;

import Modules.CommonModels.exceptions.ServerExceptions;
import Modules.CommonModels.response.ApiResponse;
import Modules.CommonModels.retrivels.ApiRetrieve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

import static Modules.CommonModels.enums.helperConstants.*;
import static Modules.CommonModels.response.ApiResponse.apiConnector;

@Component
public class DataRetrieve {

    @Autowired
    private ApiRetrieve apiRetrieve;

    public List<String> getFileNamesUpdatedWithStatus(String status) throws ServerExceptions {
        return switch (status) {
            case VALIDATED -> getDataByStatusName(VALIDATED);
            case FILES_INVALID -> getDataByStatusName(FILES_INVALID);
            case FILES_VALID -> getDataByStatusName(FILES_VALID);
            default -> throw new ServerExceptions("Invalid status name");
        };
    }

    private List<String> getDataByStatusName(String status) {
        String url = apiRetrieve.apiURLs().get(status);

        Object rawData = apiConnector(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponse<List<String>>>() {
                }).getData();

        if (rawData instanceof List<?>) {
            return ((List<?>) rawData).stream()
                    .filter(e -> e instanceof String)
                    .map(e -> (String) e)
                    .toList();
        } else {
            return List.of();
        }
    }
}
