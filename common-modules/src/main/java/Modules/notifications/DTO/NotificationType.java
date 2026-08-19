package Modules.notifications.DTO;

import lombok.Getter;

@Getter
public enum NotificationType {
    EMAIL("EMAIL"),
    TELEGRAM("TELEGRAM"),
    SMS("SMS"),
    ALL("ALL");

    private final String notificationType;

    private NotificationType(String type){
        this.notificationType =type;
    }
}
