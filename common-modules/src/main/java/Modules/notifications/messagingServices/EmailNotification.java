package Modules.notifications.messagingServices;

import Modules.CommonModels.exceptions.FunMarketException;
import Modules.CommonModels.exceptions.ServerExceptions;
import Modules.notifications.DTO.MessagePayload;
import Modules.notifications.DTO.NotificationType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service(value = "emailNotification")
public class EmailNotification implements NotificationService {

    private JavaMailSender mailSender;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Logger log = LoggerFactory.getLogger(EmailNotification.class);

    public EmailNotification(JavaMailSender javaMailSender) {
        mailSender = javaMailSender;
    }

    @Override
    public void sendNotification(MessagePayload payload) {
        log.info("Sending email notification");
        final Set<String> triggerAddress = payload.getTriggerAddress();

        try {
            log.info("Building action structure");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            mimeMessageHelper.setTo(convertToArray(payload.getTriggerAddress()));
            mimeMessageHelper.setSubject(payload.getSubject());
            mimeMessageHelper.setText(payload.getNotificationBody(), payload.isHTML());
            mimeMessageHelper.setFrom(payload.getFrom());
            mimeMessageHelper.setCc(convertToArray(payload.getCc()));
            mimeMessageHelper.setBcc(convertToArray(payload.getBcc()));

            mailSender.send(message);


        } catch (MessagingException e) {
            throw new FunMarketException("Unable structure HTML email " + e.getMessage());
        }
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.EMAIL;
    }

    private String[] convertToArray(Set<String> emails) {
        try {
            validateActionAddress(emails);
            return emails.stream()
                    .toArray(String[]::new);
        } catch (ServerExceptions e) {
            throw new FunMarketException(e.getMessage());
        }
    }

    private boolean validateActionAddress(Set<String> email) throws ServerExceptions {
        List<String> invalidEmail = new ArrayList<>();
        if (email == null && email.isEmpty()) {
            throw new ServerExceptions("Cannot process the Empty set");
        }
        Boolean outcome = email.stream()
                .filter(n -> {
                    boolean result = EMAIL_PATTERN.matcher(n).matches();
                    if (!result) {
                        log.warn("Invalid email found [Email: {}], removing email from the list", n);
                        invalidEmail.add(n);
                        return false;
                    }
                    return true;
                }).toList().size() == email.size();

        if (!invalidEmail.isEmpty()) {
            throw new ServerExceptions("Found invalid emails " + invalidEmail);
        }
        return outcome;
    }
}

