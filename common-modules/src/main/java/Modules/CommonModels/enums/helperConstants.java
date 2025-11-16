package Modules.CommonModels.enums;

import Modules.CommonModels.retrivels.ApiRetrieve;

public class helperConstants {

    private static final String APPLICATION_PROD_PROPERTIES = "application-prod.properties";
    private static final String APPLICATION_DEV_PROPERTIES = "application-dev.properties";

    private static final String VALIDATION_STATUS_PATH = "fun.market.file.get.file.by.validation.status.uri";

    public static final String MONTHLY_PERFORMANCE = "monthlyPerformance";
    public static final String FILES_VALID="VALID";
    public static final String FILES_TO_UPDATE = "AT_TO_UPDATE";
    public static final String VALIDATED="VALIDATED";
    public static final String FILES_INVALID = "INVALID";
    public static final String SAVE_EVENT = "saveEvent";

    public static final String VALIDATION_STATUS_URL = ApiRetrieve.applicationPropertiesReader(APPLICATION_PROD_PROPERTIES, VALIDATION_STATUS_PATH);

}
