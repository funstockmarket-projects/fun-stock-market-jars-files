package org.app.gitReader.GitReader.gitRetrivels;

import Modules.CommonModels.enums.FileStatus;
import Modules.CommonModels.enums.MarketEvents;
import Modules.CommonModels.enums.Validations;
import Modules.CommonModels.exceptions.ServerExceptions;
import Modules.CommonModels.model.FileDetails;
import Modules.CommonModels.model.MarketEvent;
import Modules.CommonModels.pojo.FileName;
import Modules.CommonModels.response.ApiResponse;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.app.gitReader.GitReader.apiRetrivels.DataRetrieve;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static Modules.CommonModels.enums.helperConstants.SAVE_EVENT;
import static Modules.CommonModels.response.ApiResponse.apiConnector;
import static Modules.CommonModels.response.ApiResponse.apiURLs;

@Slf4j
public class CommonRetrievals {

    static private final RestTemplate restTemplate = new RestTemplate();
    static private final HttpHeaders headers = new HttpHeaders();
    private static final HttpEntity<String> entity;
    private static String folderName;
    private static final String token = applicationPropertiesReader("market_gitURI.properties", "marketAnalysis.gitReader.gitToken");

    static {
        log.info("CommonRetrievals Initialized");
        headers.set("Authorization", "token " + token);
        entity = new HttpEntity<>(headers);
    }

    protected static Map<String, FileDetails> fetchCsvDownloadUrlsAndNames(String uri) {
        if (uri == null || uri.isBlank()) {
            throw new SecurityException("Git Uri is null");
        }
        List<String> uriElements = Arrays.stream(uri.split("/")).toList();
        folderName = uriElements.get(uriElements.size() - 1);
        log.info("Fetching FileDetails Through GitHub URI: {}", uri);
        uri = uri.trim();
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                });

        assert response.getBody() != null;

        //data retrieving from the DataBase MySql
        List<String> fileNamesUpdatedWithStatus = DataRetrieve.fileNamesUpdatedWithStatus;

        Map<String, FileDetails> fileNameAndUri = response.getBody().stream()
                .filter(filename -> {
                    String fileName = ((String) (filename.get("name"))).replace(".csv", "");
                    return !fileNamesUpdatedWithStatus.contains(fileName);
                })
                .collect(Collectors.toMap(
                        k -> ((String) (k.get("name"))).replace(".csv", ""),
                        v -> {
                            FileDetails fileDetails = new FileDetails();

                            String filename = ((String) (v.get("name"))).replace(".csv", "");
                            String fileType = ((String) v.get("name")).substring(((String) v.get("name")).lastIndexOf('.') + 1);
                            String download_url = (String) v.get("download_url");
                            Number sizeNum = (Number) v.get("size");
                            long size = sizeNum != null ? sizeNum.longValue() : 0L;

                            fileDetails.setFileName(filename);
                            fileDetails.setFileType(fileType);
                            fileDetails.setUri(download_url);
                            fileDetails.setFileSize(size);
                            fileDetails.setFolderName(folderName);
                            fileDetails.setLocalDateTime(LocalDateTime.now());

                            List<String> alert = gitRetrieveFileValidations(fileDetails);

                            if (!alert.contains(" :=Git validation Done")) {
                                fileDetails.setFileValidationStatus(Validations.GIT_FILE_VALIDATION_FALSE);
                                fileDetails.setFileStatus(FileStatus.IN_PROGRESS);
                            } else {
                                fileDetails.setFileValidationStatus(Validations.GIT_FILE_VALIDATION_TRUE);
                                fileDetails.setFileStatus(FileStatus.IN_PROGRESS);
                            }
                            fileDetails.setMessageAlert(alert);

                            return fileDetails;
                        }
                ));


        if (fileNameAndUri.isEmpty()) {
            log.info("No Incomplete files founded in this folder: {}", folderName);
            return Map.of();
        }
        return fileNameAndUri;
    }

    private static List<String> gitRetrieveFileValidations(FileDetails fileDetails) {
        List<String> alert = new ArrayList<>();
        if (fileDetails.getFileName() == null || fileDetails.getFileName().isBlank())
            alert.add(" :=FileName is empty");
        if (fileDetails.getFileType() == null || fileDetails.getFileType().isBlank())
            alert.add(" :=fileType is empty");
        if (fileDetails.getUri() == null || fileDetails.getUri().isEmpty())
            alert.add(" :=download_url is empty");
        if (fileDetails.getFolderName() == null || fileDetails.getFolderName().isBlank())
            alert.add(" := Folder Name is Empty");
        if (fileDetails.getFileSize() <= 0)
            alert.add(" :=file size is less than or equal to zero");
        if (alert.isEmpty())
            alert.add(" :=Git validation Done");
        return alert;
    }

    protected static Map<String, List<Map<String, Object>>> allEventsRetrieval(String uri) throws ServerException {
        log.info("All Events Retrieval Initialized");
        if (uri == null || uri.isBlank()) {
            log.info("URI is blank, fetching from application properties file");
            return Map.of();
        }
        Map<String, FileDetails> fetchCsvDownloadUrlsAndNames = fetchCsvDownloadUrlsAndNames(uri);
        if (fetchCsvDownloadUrlsAndNames.isEmpty()) {
            log.info("There is not files to be processed. size {}, in this Git Folder: {}", 0, folderName);
            return Map.of();
        }
        List<String> getUrl = fetchCsvDownloadUrlsAndNames.values()
                .stream()
                .map(FileDetails::getUri)
                .map(String::valueOf)
                .toList();
        List<String> getFileName = fetchCsvDownloadUrlsAndNames.keySet().stream().toList();


        Map<String, List<Map<String, Object>>> allEventsRetrieval = new HashMap<>();

        for (int i = 0; i < getUrl.size(); i++) {
            try {
                List<String[]> fetchData = readCsvFromUrl(getUrl.get(i));
                List<String> jsonName = Arrays.asList(fetchData.get(0));
                fetchData.remove(0);

                String fileName = getFileName.get(i);
                FileName file = new FileName(fileName, FileStatus.INCOMPLETE);
                List<Map<String, Object>> fileData = getDataList(fetchData, jsonName);
                log.info(
                        "File: {} Processed with {} records.",
                        fileName,
                        fileData.size()
                );
                allEventsRetrieval.put(file.getFileName(), fileData);
            } catch (IOException | CsvException e) {
                System.out.println("Unable to fetch the data.");
            }
        }
        if (!allEventsRetrieval.isEmpty()) {
            log.info("Performing Market Event, EventName: {}", folderName);
            fetchCsvDownloadUrlsAndNames = fileDataValidation(allEventsRetrieval, fetchCsvDownloadUrlsAndNames);
            if (!fetchCsvDownloadUrlsAndNames.isEmpty()) {
                MarketEvent marketEvents = convertingDataIntoMarketEventObject(fetchCsvDownloadUrlsAndNames);
            }
        }
        return allEventsRetrieval;
    }

    private static MarketEvent convertingDataIntoMarketEventObject(Map<String, FileDetails> fetchCsvDownloadUrlsAndNames) {
        log.info("Processing the market Event");
        if (fetchCsvDownloadUrlsAndNames.isEmpty()) {
            log.info("Can't convert because object is null");
            return new MarketEvent();
        }
        log.info("Processing the market Event of size: {}", fetchCsvDownloadUrlsAndNames.size());
        List<FileDetails> fileDetails = fetchCsvDownloadUrlsAndNames.values().stream().toList();
        String stringEventName = fileDetails.get(0).getFolderName();

        MarketEvent marketEvent = new MarketEvent();
        log.info("Getting Market EventName");
        switch (stringEventName) {
            case "weeklyPerformance" -> marketEvent.setMarketEventName(MarketEvents.WEEKLY);
            case "yearlyPerformance" -> marketEvent.setMarketEventName(MarketEvents.YEARLY);
            case "monthlyPerformance" -> marketEvent.setMarketEventName(MarketEvents.MONTHLY);
            case "dailyPerformance" -> marketEvent.setMarketEventName(MarketEvents.DAILY);
            default ->
                    throw new RuntimeException(new ServerExceptions("Can't process the Event EventName: " + folderName));
        }
        log.info("Marker Event Name Noted: {}", marketEvent.getMarketEventName());
        marketEvent.setFileDetails(fileDetails);

        log.info("Saving the Market Event Data EventName: {}", marketEvent.getMarketEventName());
        final String uri = apiURLs.get(SAVE_EVENT);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MarketEvent> requestEntity = new HttpEntity<>(marketEvent, headers);


        Object rowData = apiConnector(uri, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<ApiResponse<MarketEvent>>() {
        }).getData();

        if (rowData instanceof MarketEvent) {
            marketEvent = (MarketEvent) rowData;
        }
        if (marketEvent.getMarketEventId() == null) {
            log.info("Failed to Create MarketEvent. With Name: {}", marketEvent.getMarketEventName());
            return new MarketEvent();
        } else {
            log.info("Market Event Created. With Name: {}", marketEvent.getMarketEventName());
            return marketEvent;
        }

    }

    private static Map<String, FileDetails> fileDataValidation(Map<String, List<Map<String, Object>>> allEventsRetrieval, Map<String, FileDetails> fileDataValidation) {
        if (allEventsRetrieval.isEmpty() || fileDataValidation.isEmpty()) {
            log.info("allEventsRetrieval and fileDataValidation is null, Can't process..");
            return Map.of();
        }
        log.info("performing validation for file data TotalFileData: {}, TotalFiles: {}", allEventsRetrieval.size(), fileDataValidation.size());
        //processing the records
        allEventsRetrieval.forEach((filename, details) -> {
            FileDetails fileDetails = fileDataValidation.get(filename);
            if (fileDetails != null) {

                //checking the number of records
                List<String> alert = fileDetails.getMessageAlert();
                if (details.isEmpty()) {
                    alert.add(" :=File data is empty");
                    fileDetails.setFileValidationStatus(Validations.GIT_FILE_VALIDATION_FALSE);
                    fileDetails.setFileStatus(FileStatus.IN_PROGRESS);
                }


                fileDetails.setMessageAlert(alert);
                fileDetails.setNumberOfRecords(details.size());

                //ToDO: Further validations can be added here
            }


        });


        return fileDataValidation;
        //TODO Implementation of validating for file data;
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

    public static String applicationPropertiesReader(String fileName, String key) {
        log.info("Reading application properties from file: {} for key: {}", fileName, key);
        Properties properties = new Properties();
        try (InputStream input = CommonRetrievals.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                log.info("Sorry, unable to find market_gitURI.properties");
            }
            properties.load(input);
            String value = properties.getProperty(key);
            if (value == null) {
                log.error("Key not found: {}", key);
            }
            return value;
        } catch (IOException ex) {
            log.error("IOException occurred: {}", ex.getMessage());
        }
        return null;
    }
}
