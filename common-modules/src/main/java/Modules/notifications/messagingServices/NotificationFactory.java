package Modules.notifications.messagingServices;

import Modules.CommonModels.exceptions.FunMarketException;
import Modules.notifications.DTO.NotificationType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component(value="notificationFactory")
public class NotificationFactory {

    private final Map<NotificationType, NotificationService> notificationFactory;

    public NotificationFactory(List<NotificationService> notificationServiceList){
        this.notificationFactory = notificationServiceList.stream()
                .collect(Collectors.toMap(NotificationService::getNotificationType, Function.identity()));
    }

    public NotificationService getService(NotificationType notificationType){
        NotificationService notificationService = notificationFactory.getOrDefault(notificationType,null);
        if(notificationService == null){
            throw new FunMarketException("Invalid configuration. ");
        }
        return notificationService;
    }
}
