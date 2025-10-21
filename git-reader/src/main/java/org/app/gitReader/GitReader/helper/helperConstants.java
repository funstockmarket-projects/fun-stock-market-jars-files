package org.app.gitReader.GitReader.helper;

import org.app.gitReader.GitReader.gitRetrivels.CommonRetrievals;

public class helperConstants {

    public static final String APPLICATION_PROPERTIES = "market_gitURI.properties";
    public static final String MONTHLY_PATH = "marketAnalysis.gitReader.gitMonthlyPath";
    public static final String WEEKLY_PATH = "marketAnalysis.gitReader.gitWeeklyPath";
    public static final String DAILY_PATH = "marketAnalysis.gitReader.gitDailyPath";
    public static final String YEARLY_PATH = "marketAnalysis.gitReader.gitYearlyPath";


    public static final String WEEKLY_URI = CommonRetrievals.applicationPropertiesReader(APPLICATION_PROPERTIES, WEEKLY_PATH);
    public static final String MONTHLY_URI = CommonRetrievals.applicationPropertiesReader(APPLICATION_PROPERTIES, MONTHLY_PATH);
    public static final String DAILY_URI = CommonRetrievals.applicationPropertiesReader(APPLICATION_PROPERTIES, DAILY_PATH);
    public static final String YEARLY_URI = CommonRetrievals.applicationPropertiesReader(APPLICATION_PROPERTIES, YEARLY_PATH);

}
