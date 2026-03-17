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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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


    public static final ZoneId ZONE = ZoneId.of("Asia/Kolkata");
    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a z");

    public NotificationsStatusCodes telegramMessage(String message) {
        return sendMessage(message);
    }

    private NotificationsStatusCodes sendMessage(String message) {

        log.info("initiating telegram notification dispatch. Validating the message");

        if(message==null || message.isBlank() || message.length() <10){
            log.info("Invalid message: {}", message);
            return NotificationsStatusCodes.INVALID_MESSAGE;
        }

        String urlString = prepareUri(botUri,token, chatId, message);

        log.info("validating the urlString: {}", urlString);
        if(urlString== null || urlString.isBlank()){
            log.warn("telegram uri failed uri: {}", urlString);
            return NotificationsStatusCodes.URI_FAILED;
        }

        return connectToServerSendMessage(urlString);
    }

    private String prepareUri(String botUri, String token, String chatId, String message){


        if(botUri== null || botUri.isBlank() || token == null || token.isBlank() || chatId== null || chatId.isBlank()){
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

    private String preparingEnCodeMessageForUri(String message){
        return URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    private NotificationsStatusCodes connectToServerSendMessage(String uri){
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int response = connection.getResponseCode();
            if(response == 200){
                log.info("Message Sent. Time: {}", telegramTimeNow());
                return NotificationsStatusCodes.SEND;
            }else{
                log.info("Message Process Fail. Time: {}", telegramTimeNow());
                return NotificationsStatusCodes.PROCESS_FAILED;
            }
        } catch (Exception e) {
            log.info("Message Fail T0 Sent. Time: {}", telegramTimeNow());
            return NotificationsStatusCodes.FAILED;
        }
    }

    public static String telegramTimeNow() {
        return ZonedDateTime.now(ZONE).format(FORMATTER);
    }
}
