package org.retrieve;

import Modules.CommonModels.model.Holdings;
import Modules.CommonModels.response.ApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import static Modules.CommonModels.enums.helperConstants.FILES_INCOMPLETE;
import static Modules.CommonModels.response.ApiResponse.apiConnector;
import static Modules.CommonModels.response.ApiResponse.apiURLs;

public class DataRetrieve {

    public static Holdings monthlyPerformance;

   static{
       String url = apiURLs.get(FILES_INCOMPLETE);

       Object rawData = apiConnector(url, HttpMethod.GET, null, new ParameterizedTypeReference< ApiResponse<Holdings>>(){}).getData();
       if (rawData instanceof Holdings) {
           monthlyPerformance = (Holdings) rawData;
       }else{
           monthlyPerformance = new Holdings();
       }
   }
}
