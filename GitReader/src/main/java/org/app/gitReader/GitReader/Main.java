package org.app.gitReader.GitReader;

import org.app.gitReader.GitReader.gitRetrivels.AllEventsRetrieval;

import static org.app.gitReader.GitReader.helper.helperConstants.WEEKLY_URI;

public class Main {
    public static void main(String[] args) {
        AllEventsRetrieval allEventsRetrieval =new AllEventsRetrieval();
        allEventsRetrieval.returnAllEvents();

    }
}
