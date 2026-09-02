package Modules.notifications.messagingServices;

import Modules.CommonModels.exceptions.FunMarketException;
import Modules.notifications.DTO.MessagePayload;
import Modules.notifications.DTO.NotificationType;
import Modules.notifications.NotificationsStatusCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;

@Service
public class Notification {

    private final NotificationFactory notificationFactory;
    private Object lockNotification = new Object();
    private final static Logger log = LoggerFactory.getLogger(Notification.class);

    public Notification(NotificationFactory notificationFactory){
        this.notificationFactory = notificationFactory;
    }

    public NotificationsStatusCodes sendNotification(MessagePayload payload, NotificationType notificationType) {
        try {
            notificationFactory.getService(notificationType)
                    .sendNotification(payload);

            log.info("Notification sent successfully [Thread: {}, Type: {}]",
                    Thread.currentThread().getName(), notificationType);

            return NotificationsStatusCodes.SEND;

        } catch (FunMarketException e) {
            log.warn("Notification Failed [Thread: {}, Type: {}]: {}",
                    Thread.currentThread().getName(), notificationType, e.getMessage());
            return NotificationsStatusCodes.FAILED;

        } catch (Exception e) {
            log.error("Unexpected error sending notification [Thread: {}]: {}",
                    Thread.currentThread().getName(), e.getMessage(), e);
            return NotificationsStatusCodes.FAILED;
        }
    }
}
