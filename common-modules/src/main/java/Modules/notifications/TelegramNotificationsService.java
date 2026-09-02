package Modules.notifications;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

import static Modules.CommonModels.pojo.TimeConverter.timeConverterToIST;

@Slf4j
@Component
@Getter
@Setter
public class TelegramNotificationsService {

    @Value("${telegram.token}")
    private String token;

    @Value("${telegram.chatId}")
    private String chatId;

    @Value("${telegram.botUri}")
    private String botUri;

    @Value("${funmarket.notificationSwitch}")
    private boolean notificationSwitch;

    public NotificationsStatusCodes sendMessage(Map<String, String> messageElements, Class<?> className) {
        log.info("Collecting message information");
        return buildMessage(messageElements, className);
    }

    public NotificationsStatusCodes telegramMessage(String message) {

        if (notificationSwitch) {
            return sendMessage(message);
        } else {
            return null;
        }
    }

    private NotificationsStatusCodes sendMessage(String message) {

        log.info("initiating telegram notification dispatch. Validating the message");

        if (message == null || message.isBlank() || message.length() < 10) {
            log.info("Invalid message: {}", message);
            return NotificationsStatusCodes.INVALID_MESSAGE;
        }

        String urlString = prepareUri(botUri, token, chatId, message);

        if (urlString == null || urlString.isBlank()) {
            log.warn("telegram uri failed uri: {}", urlString);
            return NotificationsStatusCodes.URI_FAILED;
        }

        return connectToServerSendMessage(urlString);
    }

    private String prepareUri(String botUri, String token, String chatId, String message) {


        if (botUri == null || botUri.isBlank() || token == null || token.isBlank() || chatId == null || chatId.isBlank()) {
            log.info("Telegram preparing with: botUri: {}, token: {}, chatId: {}, Invalid telegram Uri String", botUri, token, chatId);
            return null;
        }
        return botUri.concat(token)
                .concat("/sendMessage?chat_id=")
                .concat(chatId)
                .concat("&parse_mode=HTML")
                .concat("&text=")
                .concat(preparingEnCodeMessageForUri(message));
    }

    private String preparingEnCodeMessageForUri(String message) {

        message = message + " \n\n \uD83E\uDD16 <i>Fun Market Automation System</i>";
        return URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    private NotificationsStatusCodes connectToServerSendMessage(String uri) {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int response = connection.getResponseCode();
            if (response == 200) {
                log.info("Message Sent. Time: {}", telegramTimeNow());
                return NotificationsStatusCodes.SEND;
            } else {
                log.info("Message Process Fail. Time: {}", telegramTimeNow());
                return NotificationsStatusCodes.PROCESS_FAILED;
            }
        } catch (Exception e) {
            log.info("Message Fail T0 Sent. Time: {}", telegramTimeNow());
            return NotificationsStatusCodes.FAILED;
        }
    }

    private NotificationsStatusCodes buildMessage(Map<String, String> messageElements, Class<?> className) {

        if (className == null || messageElements == null || messageElements.isEmpty()) {
            log.info("Invalid message elements count: {}",
                    messageElements == null ? 0 : messageElements.size());
            return null;
        }

        String classNotifications = "\uD83D\uDCC1 " + className.getSimpleName();

        int maxLength = messageElements.keySet()
                .stream()
                .mapToInt(String::length)
                .max()
                .orElse(0) - 1;

        StringBuilder message = new StringBuilder();
        message.append("<b> \uD83D\uDD25THE FUN STOCK MARKET \uD83D\uDD25</b>").append("\n\n")
                .append("<b><i>").append(classNotifications).append("</i>\n")
                .append("-".repeat(classNotifications.length())).append("</b>").append("\n\n");

        messageElements.forEach((key, value) -> {
            int spacesNeeded = maxLength - key.length() - 1;

            message.append("<b>").append(key).append("</b>")//.append(" ".repeat(Math.max(0, spacesNeeded)))
                    .append(" : ")
                    .append(value)
                    .append("\n");
        });
        return telegramMessage(message.toString());
    }

    public static String telegramTimeNow() {
        return timeConverterToIST(LocalDateTime.now().toString());
    }
}
