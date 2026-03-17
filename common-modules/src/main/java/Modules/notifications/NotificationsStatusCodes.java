package Modules.notifications;

public enum NotificationsStatusCodes {

    SEND("send", "200"),
    FAILED("failed", "300"),
    INVALID_MESSAGE("Invalid message. Null or invalid", "301"),
    PROCESS_FAILED("in_process", "5000"),
    URI_FAILED("Telegram URI Validation Failed", "501");

    private String status;
    private String statusCode;

    NotificationsStatusCodes(String inProcess, String statusCode) {
    }
}
