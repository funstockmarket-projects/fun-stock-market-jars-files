package Modules.notifications.messagingServices;

import Modules.notifications.DTO.MessagePayload;
import Modules.notifications.DTO.NotificationType;

import java.rmi.ServerException;

public interface NotificationService {

    void sendNotification(MessagePayload payload) throws ServerException;
    NotificationType getNotificationType();
}
