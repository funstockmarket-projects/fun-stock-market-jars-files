package org.app.gitReader.GitReader.gitRetrivels;

import Modules.CommonModels.enums.FileStatus;
import Modules.CommonModels.exceptions.ServerExceptions;
import Modules.CommonModels.pojo.FileName;
import Modules.CommonModels.retrivels.ApiRetrieve;
import com.fsm.domainsMapping.businessObject.stockDetailsBO.FileMetadataBO;
import com.fsm.domainsMapping.constantsBO.MarketEventsBO;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.gitReader.GitReader.apiRetrivels.DataRetrieve;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static Modules.CommonModels.enums.FileValidationStatus.CLEARED;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonRetrievals {

    static private final RestTemplate restTemplate = new RestTemplate();
    static private final HttpHeaders headers = new HttpHeaders();
    private static HttpEntity<String> entity;
    private static String folderName;
    @Value("${marketAnalysis.gitReader.file.keys}")
    private String token;

    private final DataRetrieve dataRetrieve;

    protected Map<String, FileMetadataBO> fetchCsvDownloadUrlsAndNames(String uri) {
        log.info("CommonRetrievals Initialized");
        headers.set("Authorization", "token " + token);
        entity = new HttpEntity<>(headers);
        System.out.println(token);
        if (uri == null || uri.isBlank()) {
            throw new SecurityException("Git Uri is null");
        }
        List<String> uriElements = Arrays.stream(uri.split("/")).toList();
        folderName = uriElements.get(uriElements.size() - 1);
        log.info("Fetching MarketFileDetails Through GitHub URI: {}", uri);
        uri = uri.trim();
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                });
        assert response.getBody() != null;
        //data retrieving from the DataBase server for valid file
        List<String> fileNamesUpdatedWithStatus = List.of();
        try {
            fileNamesUpdatedWithStatus = dataRetrieve.getFileNamesUpdatedWithStatus(CLEARED.getStatus());
        } catch (Exception e) {
            log.error("Error while fetching fileNamesUpdatedWithStatus message: {}", e.getMessage());
        }
        List<String> finalFileNamesUpdatedWithStatus = fileNamesUpdatedWithStatus;
        Map<String, FileMetadataBO> fileNameAndUri = response.getBody().stream()
                .filter(filename -> {
                    String fileName = ((String) (filename.get("name"))).replace(".csv", "");
                    return !finalFileNamesUpdatedWithStatus.contains(fileName);
                })
                .collect(Collectors.toMap(
                        k -> ((String) (k.get("name"))).replace(".csv", ""),
                        v -> {
                            String filename = ((String) (v.get("name"))).replace(".csv", "");
                            String fileType = ((String) v.get("name")).substring(((String) v.get("name")).lastIndexOf('.') + 1);
                            String download_url = (String) v.get("download_url");
                            Number sizeNum = (Number) v.get("size");
                            long size = sizeNum != null ? sizeNum.longValue() : 0L;
                            return FileMetadataBO.builder()
                                    .fileName(filename)
                                    .fileType(fileType)
                                    .uri(download_url)
                                    .fileSize(size)
                                    .folderName(folderName)
                                    .fileModifiedDate(LocalDateTime.now())
                                    .build();
                        }
                ));
        if (fileNameAndUri.isEmpty()) {
            log.info("No Incomplete files founded in this folder: {}", folderName);
            return Map.of();
        } else {
            return fileNameAndUri; //filename, fileDetails
        }
    }

    protected Map<String, FileMetadataBO> allEventsRetrieval(String uri) throws ServerException {
        log.info("All Events Retrieval Initialized");
        if (uri == null || uri.isBlank()) {
            log.info("URI is blank, fetching from application properties file");
            return Map.of();
        }

        Map<String, FileMetadataBO> fetchCsvDownloadUrlsAndNames = fetchCsvDownloadUrlsAndNames(uri); //fileName, fileDetails
        if (fetchCsvDownloadUrlsAndNames.isEmpty() || fetchCsvDownloadUrlsAndNames == null) {
            log.info("There is not files to be processed. size {}, in this Git Folder: {}", 0, folderName);
            return Map.of();
        }

        List<String> getUrl = fetchCsvDownloadUrlsAndNames.values()
                .stream()
                .map(FileMetadataBO::getUri)
                .map(String::valueOf)
                .toList();
        List<String> getFileName = fetchCsvDownloadUrlsAndNames.keySet().stream().toList();
        Map<String, List<Map<String, Object>>> allEventsRetrieval = new HashMap<>(); //fileName, fileData
        for (int i = 0; i < getUrl.size(); i++) {
            try {
                List<String[]> fetchData = readCsvFromUrl(getUrl.get(i));
                List<String> jsonName = Arrays.asList(fetchData.get(0));
                fetchData.remove(0);
                String fileName = getFileName.get(i);
                FileName file = new FileName(fileName, FileStatus.INCOMPLETE);
                List<Map<String, Object>> fileData = getDataList(fetchData, jsonName);
                log.info("File: {} Processed with {} records.", fileName, fileData.size());
                allEventsRetrieval.put(file.getFileName(), fileData);
            } catch (IOException | CsvException e) {
                System.out.println("Unable to fetch the data.");
            }
        }
        if (!allEventsRetrieval.isEmpty()) {
            log.info("Performing Market Event, EventName: {}", folderName);
            fetchCsvDownloadUrlsAndNames = fileDataValidation(allEventsRetrieval, fetchCsvDownloadUrlsAndNames);
        }
        return fetchCsvDownloadUrlsAndNames;
    }

    private static Map<String, FileMetadataBO> fileDataValidation(Map<String, List<Map<String, Object>>> allEventsRetrieval, Map<String, FileMetadataBO> fetchCsvDownloadUrlsAndNames) {
        if (allEventsRetrieval.isEmpty() || fetchCsvDownloadUrlsAndNames.isEmpty()) {
            log.info("allEventsRetrieval and fetchCsvDownloadUrlsAndNames is null, Can't process..");
            return Map.of();
        }
        log.info("performing validation for file data TotalFileData: {}, TotalFiles: {}", allEventsRetrieval.size(), fetchCsvDownloadUrlsAndNames.size());
        //processing the records
        allEventsRetrieval.forEach((filename, details) -> {
            FileMetadataBO fileDetails = fetchCsvDownloadUrlsAndNames.get(filename);
            if (fileDetails != null) {
                fileDetails.setFileData(details);
                String stringEventName = fileDetails.getFolderName();

                switch (stringEventName) {
                    case "weeklyPerformance" -> fileDetails.setEventNameBO(MarketEventsBO.WEEKLY);
                    case "yearlyPerformance" -> fileDetails.setEventNameBO(MarketEventsBO.YEARLY);
                    case "monthlyPerformance" -> fileDetails.setEventNameBO(MarketEventsBO.MONTHLY);
                    case "dailyPerformance" -> fileDetails.setEventNameBO(MarketEventsBO.DAILY);
                    default ->
                            throw new RuntimeException(new ServerExceptions("Can't process the Event EventName: " + folderName));
                }
                log.info("Marker Event Name Noted: {}", fileDetails.getEventNameBO().getEventName());
                log.info("Saving the Market Event Data EventName: {}", fileDetails.getEventNameBO().getEventName());
                fileDetails.setNumberOfRecords(details.size());
            }
        });
        return fetchCsvDownloadUrlsAndNames;
    }

    protected static List<String[]> readCsvFromUrl(String csvUrl) throws IOException, CsvException {
        log.info("Reading CSV from URL: {}", csvUrl);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(csvUrl).openStream()));

             CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll();
        }
    }

    private static List<Map<String, Object>> getDataList(List<String[]> fetchData, List<String> jsonName) {
        List<Map<String, Object>> weeklyDataList = new ArrayList<>();
        for (String[] fetchDatum : fetchData) {
            Map<String, Object> weeklyDataMap = new HashMap<>();
            for (int k = 0; k < jsonName.size(); k++) {
                String key = jsonName.get(k).replace(".", "").trim().replace(" ", "").toLowerCase();
                weeklyDataMap.put(key, fetchDatum[k]);
            }
            weeklyDataMap.remove("");
            weeklyDataList.add(weeklyDataMap);
        }
        return weeklyDataList;
    }
}