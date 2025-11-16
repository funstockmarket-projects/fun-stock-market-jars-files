package org.app.gitReader.GitReader.gitRetrivels;

import Modules.CommonModels.enums.FileStatus;
import Modules.CommonModels.enums.FileValidationReasons;
import Modules.CommonModels.enums.MarketEvents;
import Modules.CommonModels.enums.Validations;
import Modules.CommonModels.exceptions.ServerExceptions;
import Modules.CommonModels.model.marketStockData.FileDateValidationStatus;
import Modules.CommonModels.model.marketStockData.StockFileDetails;
import Modules.CommonModels.pojo.FileName;
import Modules.CommonModels.retrivels.ApiRetrieve;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.app.gitReader.GitReader.apiRetrivels.DataRetrieve;
import org.springframework.beans.factory.annotation.Autowired;
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

import static Modules.CommonModels.enums.helperConstants.VALIDATED;

@Slf4j
@Component
public class CommonRetrievals {

    static private final RestTemplate restTemplate = new RestTemplate();
    static private final HttpHeaders headers = new HttpHeaders();
    private static final HttpEntity<String> entity;
    private static String folderName;
    private static final String token = ApiRetrieve.applicationPropertiesReader("market_gitURI.properties", "marketAnalysis.gitReader.gitToken");

    @Autowired
    private DataRetrieve dataRetrieve;

    static {
        log.info("CommonRetrievals Initialized");
        headers.set("Authorization", "token " + token);
        entity = new HttpEntity<>(headers);
    }

    protected Map<String, StockFileDetails> fetchCsvDownloadUrlsAndNames(String uri) {
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
            fileNamesUpdatedWithStatus = dataRetrieve.getFileNamesUpdatedWithStatus(VALIDATED);
        } catch (Exception e) {
            log.error("Error while fetching fileNamesUpdatedWithStatus message: {}", e.getMessage());
        }
        List<String> finalFileNamesUpdatedWithStatus = fileNamesUpdatedWithStatus;
        Map<String, StockFileDetails> fileNameAndUri = response.getBody().stream()
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
                            StockFileDetails stockFileDetails = StockFileDetails.builder()
                                    .fileName(filename)
                                    .fileType(fileType)
                                    .uri(download_url)
                                    .fileSize(size)
                                    .folderName(folderName)
                                    .fileModifiedDate(LocalDateTime.now())
                                    .build();

                            List<FileValidationReasons> alert = gitRetrieveFileValidations(stockFileDetails);
                            FileDateValidationStatus fileDateValidationStatus;
                            if (alert.contains(FileValidationReasons.GIT_FILE_VALIDATION_SUCCESSFUL)) {
                                fileDateValidationStatus = FileDateValidationStatus.builder()
                                        .fileValidationStatus(Validations.VALID)
                                        .validationDate(LocalDateTime.now())
                                        .reason(alert)
                                        .build();
                            } else {
                                fileDateValidationStatus = FileDateValidationStatus.builder()
                                        .fileValidationStatus(Validations.GIT_FILE_VALIDATION_FALSE)
                                        .validationDate(LocalDateTime.now())
                                        .reason(alert)
                                        .build();
                            }
                            stockFileDetails.setFileDataValidation(fileDateValidationStatus);
                            return stockFileDetails;
                        }
                ));
        if (fileNameAndUri.isEmpty()) {
            log.info("No Incomplete files founded in this folder: {}", folderName);
            return Map.of();
        }else{
            return fileNameAndUri; //filename, fileDetails
        }
    }

    private static List<FileValidationReasons> gitRetrieveFileValidations(StockFileDetails stockFileDetails) {
        List<FileValidationReasons> alert = new ArrayList<>();
        if (stockFileDetails.getFileName() == null || stockFileDetails.getFileName().isBlank())
            alert.add(FileValidationReasons.FILENAME_INCORRECT);
        if (stockFileDetails.getFileType() == null || stockFileDetails.getFileType().isBlank())
            alert.add(FileValidationReasons.FILETYPE_INCORRECT);
        if (stockFileDetails.getUri() == null || stockFileDetails.getUri().isEmpty())
            alert.add(FileValidationReasons.DOWNLOAD_URL_INCORRECT);
        if (stockFileDetails.getFolderName() == null || stockFileDetails.getFolderName().isBlank())
            alert.add(FileValidationReasons.FOLDER_NAME_INCORRECT);
        if (stockFileDetails.getFileSize() <= 0)
            alert.add(FileValidationReasons.FILE_SIZE);
        if (alert.isEmpty())
            alert.add(FileValidationReasons.GIT_FILE_VALIDATION_SUCCESSFUL);
        return alert;
    }

    protected Map<String, StockFileDetails> allEventsRetrieval(String uri) throws ServerException {
        log.info("All Events Retrieval Initialized");
        if (uri == null || uri.isBlank()) {
            log.info("URI is blank, fetching from application properties file");
            return Map.of();
        }

        Map<String, StockFileDetails> fetchCsvDownloadUrlsAndNames = fetchCsvDownloadUrlsAndNames(uri); //fileName, fileDetails
        if (fetchCsvDownloadUrlsAndNames.isEmpty()) {
            log.info("There is not files to be processed. size {}, in this Git Folder: {}", 0, folderName);
            return Map.of();
        }

        List<String> getUrl = fetchCsvDownloadUrlsAndNames.values()
                .stream()
                .map(StockFileDetails::getUri)
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
            fetchCsvDownloadUrlsAndNames = fileDataValidation(allEventsRetrieval, fetchCsvDownloadUrlsAndNames);// fileName, fileDetails
        }
        return fetchCsvDownloadUrlsAndNames;
    }

    private static Map<String, StockFileDetails> fileDataValidation(Map<String, List<Map<String, Object>>> allEventsRetrieval, Map<String, StockFileDetails> fetchCsvDownloadUrlsAndNames) {
        if (allEventsRetrieval.isEmpty() || fetchCsvDownloadUrlsAndNames.isEmpty()) {
            log.info("allEventsRetrieval and fetchCsvDownloadUrlsAndNames is null, Can't process..");
            return Map.of();
        }
        log.info("performing validation for file data TotalFileData: {}, TotalFiles: {}", allEventsRetrieval.size(), fetchCsvDownloadUrlsAndNames.size());
        //processing the records
        allEventsRetrieval.forEach((filename, details) -> {
            StockFileDetails fileDetails = fetchCsvDownloadUrlsAndNames.get(filename);
            if (fileDetails != null) {
                fileDetails.setFileData(details);
                //checking the number of records
                List<FileValidationReasons> alert = fileDetails.getFileDataValidation().getReason();
                if (details.isEmpty()) {
                    alert.add(FileValidationReasons.FILE_DATA_INCORRECT);
                    FileDateValidationStatus fileDateValidationStatus = FileDateValidationStatus.builder()
                            .fileValidationStatus(Validations.GIT_FILE_VALIDATION_FALSE)
                            .validationDate(LocalDateTime.now())
                            .reason(alert)
                            .build();
                    fileDetails.setFileDataValidation(fileDateValidationStatus);
                }
                String stringEventName = fileDetails.getFolderName();

                switch (stringEventName) {
                    case "weeklyPerformance" -> fileDetails.setMarketEvents(MarketEvents.WEEKLY);
                    case "yearlyPerformance" -> fileDetails.setMarketEvents(MarketEvents.YEARLY);
                    case "monthlyPerformance" -> fileDetails.setMarketEvents(MarketEvents.MONTHLY);
                    case "dailyPerformance" -> fileDetails.setMarketEvents(MarketEvents.DAILY);
                    default ->
                            throw new RuntimeException(new ServerExceptions("Can't process the Event EventName: " + folderName));
                }
                log.info("Marker Event Name Noted: {}", fileDetails.getMarketEvents().getEventName());
                log.info("Saving the Market Event Data EventName: {}", fileDetails.getMarketEvents().getEventName());
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