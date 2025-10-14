package org.app.gitReader.GitReader.apiRetrivels;

import Modules.CommonModels.response.ApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static Modules.CommonModels.enums.helperConstants.*;
import static Modules.CommonModels.response.ApiResponse.apiConnector;
import static Modules.CommonModels.response.ApiResponse.apiURLs;

public class DataRetrieve {


    public static List<String> fileNamesUpdatedWithStatus;
    public static final boolean IS_FILES_INCOMPLETE = true;

    static {
        if(IS_FILES_INCOMPLETE){
            String url = apiURLs.get(FILES_UPDATED);


            Object rawData = apiConnector(url, HttpMethod.GET, null, new ParameterizedTypeReference<ApiResponse<List<String>>>(){}).getData();

            if (rawData instanceof List<?>) {
                fileNamesUpdatedWithStatus = ((List<?>) rawData).stream()
                        .filter(e -> e instanceof String)
                        .map(e -> (String) e)
                        .toList();
            } else {
                fileNamesUpdatedWithStatus = List.of();
            }
        }
    }
}
