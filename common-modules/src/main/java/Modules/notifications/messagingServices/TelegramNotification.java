package Modules.notifications.messagingServices;

import Modules.notifications.DTO.MessagePayload;
import Modules.notifications.DTO.NotificationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@Component(value = "telegramNotification")
public class TelegramNotification implements NotificationService {

    @Value("${fsm.telegram.notification.token}")
    private String token;
    private final RestTemplate restTemplate;

    public TelegramNotification(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendNotification(MessagePayload payload) throws ServerException {
        String url = "https://api.telegram.org/bot" + token + "/sendMessage";

        Map<String, Object> request = new HashMap<>();
        request.put("chat_id", payload.getTriggerAddress().toArray(new String[0])[0]);
        request.put("parse_mode", "HTML");

        request.put("disable_web_page_preview", true);

        String text = "<b>" + payload.getSubject() + "</b>\n\n" + payload.getNotificationBody();
        request.put("text", text);

        restTemplate.postForObject(url, request, String.class);
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.TELEGRAM;
    }
}
