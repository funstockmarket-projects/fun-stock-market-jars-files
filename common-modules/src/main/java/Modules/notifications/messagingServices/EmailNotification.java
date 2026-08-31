package Modules.notifications.messagingServices;

import Modules.CommonModels.exceptions.FunMarketException;
import Modules.CommonModels.exceptions.ServerExceptions;
import Modules.notifications.DTO.MessagePayload;
import Modules.notifications.DTO.NotificationType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component(value = "emailNotification")
public class EmailNotification implements NotificationService {

    private final JavaMailSender mailSender;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Logger log = LoggerFactory.getLogger(EmailNotification.class);

    @Value("${spring.mail.from:}")
    private String configuredFromAddress;

    public EmailNotification(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    @Override
    public void sendNotification(MessagePayload payload) {
        log.info("Initiating email dispatch process");

        if (payload == null || payload.getTriggerAddress() == null || payload.getTriggerAddress().isEmpty()) {
            throw new FunMarketException("Cannot send email without recipients");
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setHeader("X-Priority", "3");
            message.setHeader("Precedence", "bulk");
            message.setHeader("Auto-Submitted", "auto-generated");

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");

            String senderAddress = resolveFromAddress(payload);
            mimeMessageHelper.setFrom(senderAddress, "FunMarket Notifications");

            if (payload.getFrom() != null && !payload.getFrom().isBlank()
                    && !payload.getFrom().equalsIgnoreCase(senderAddress)) {
                mimeMessageHelper.setReplyTo(payload.getFrom());
            }

            // 3. Validate & set recipient arrays (TO, CC, BCC)
            mimeMessageHelper.setTo(convertToArray(payload.getTriggerAddress()));

            if (payload.getCc() != null && !payload.getCc().isEmpty()) {
                mimeMessageHelper.setCc(convertToArray(payload.getCc()));
            }

            if (payload.getBcc() != null && !payload.getBcc().isEmpty()) {
                mimeMessageHelper.setBcc(convertToArray(payload.getBcc()));
            }

            // 4. Set Subject
            String subject = (payload.getSubject() != null && !payload.getSubject().isBlank())
                    ? payload.getSubject()
                    : "FunMarket Alert";
            mimeMessageHelper.setSubject(subject);

            // 5. Handle Content (Multipart HTML + Clean Plain-Text fallback)
            String rawBody = payload.getNotificationBody() != null ? payload.getNotificationBody() : buildHtmlTemplate(subject, "Invalid Date");

            if (payload.isHTML()) {

                String plainTextFallback = stripHtmlToPlainText(rawBody);

                // Both parameters passed: Plain text fallback prevents Gmail spam flags
                mimeMessageHelper.setText(plainTextFallback, rawBody);
            } else {
                mimeMessageHelper.setText(rawBody, false);
            }

            // 6. Send Email
            mailSender.send(message);
            log.info("Email successfully sent to recipients: {}", payload.getTriggerAddress());

        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to construct or send email notification: {}", e.getMessage(), e);
            throw new FunMarketException("Unable to structure or transmit email: " + e.getMessage());
        }
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.EMAIL;
    }

    private String[] convertToArray(Set<String> emails) {
        try {
            validateActionAddress(emails);
            return emails.toArray(new String[0]);
        } catch (ServerExceptions e) {
            throw new FunMarketException(e.getMessage());
        }
    }

    private boolean validateActionAddress(Set<String> emails) throws ServerExceptions {
        if (emails == null || emails.isEmpty()) {
            throw new ServerExceptions("Cannot process empty email set");
        }

        List<String> invalidEmails = new ArrayList<>();
        for (String email : emails) {
            if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
                log.warn("Invalid email address identified: [{}]", email);
                invalidEmails.add(email);
            }
        }

        if (!invalidEmails.isEmpty()) {
            throw new ServerExceptions("Found invalid emails in payload: " + invalidEmails);
        }
        return true;
    }

    private String resolveFromAddress(MessagePayload payload) {
        if (configuredFromAddress != null && !configuredFromAddress.isBlank()) {
            return configuredFromAddress.trim();
        }
        if (payload.getFrom() != null && !payload.getFrom().isBlank()) {
            return payload.getFrom().trim();
        }
        throw new FunMarketException("No valid sender email configured for outbound mail");
    }

    private String stripHtmlToPlainText(String html) {
        if (html == null || html.isBlank()) {
            return "";
        }
        // Convert line-break producing elements to newlines before stripping tags
        return html.replaceAll("(?i)<br\\s*/?>", "\n")
                .replaceAll("(?i)</p>", "\n\n")
                .replaceAll("(?i)</li>", "\n")
                .replaceAll("<[^>]*>", "")
                .replaceAll("&nbsp;", " ")
                .trim();
    }

    private String buildHtmlTemplate(String title, String body) {
        String safeTitle = (title != null && !title.isBlank()) ? title : "FunMarket Alert";
        String safeBody = (body != null) ? body : "";

        return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <title>%s</title>
        </head>
        <body style="margin: 0; padding: 0; background-color: #f4f6f9; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; -webkit-font-smoothing: antialiased;">
            <!-- Main Background Container -->
            <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f4f6f9; padding: 20px 10px;">
                <tr>
                    <td align="center">
                        <!-- Main Card -->
                        <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="max-width: 600px; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.05); border: 1px solid #e5e7eb;">

                            <!-- Header Bar -->
                            <tr>
                                <td style="background-color: #0f172a; padding: 24px 32px; border-bottom: 3px solid #2563eb;">
                                    <h1 style="margin: 0; font-size: 20px; font-weight: 600; color: #ffffff; letter-spacing: -0.3px; line-height: 1.3;">
                                        %s
                                    </h1>
                                </td>
                            </tr>

                            <!-- Content Area -->
                            <tr>
                                <td style="padding: 32px; color: #334155; font-size: 15px; line-height: 1.6;">
                                    <style>
                                        h1, h2, h3, h4, h5, h6 { color: #0f172a; margin-top: 0; margin-bottom: 12px; font-weight: 600; }
                                        h1 { font-size: 22px; }
                                        h2 { font-size: 18px; }
                                        h3 { font-size: 16px; }
                                        p { margin-top: 0; margin-bottom: 16px; color: #334155; }
                                        ul, ol { margin-top: 0; margin-bottom: 16px; padding-left: 24px; color: #334155; }
                                        li { margin-bottom: 6px; }
                                        a { color: #2563eb; text-decoration: none; font-weight: 500; }
                                        a:hover { text-decoration: underline; }
                                        table { width: 100%%; border-collapse: collapse; margin: 16px 0; font-size: 14px; }
                                        th, td { border: 1px solid #e2e8f0; padding: 10px 12px; text-align: left; }
                                        th { background-color: #f8fafc; color: #475569; font-weight: 600; }
                                        code, pre { background-color: #f1f5f9; color: #0f172a; padding: 2px 6px; border-radius: 4px; font-family: monospace; font-size: 13px; }
                                    </style>

                                    %s
                                </td>
                            </tr>

                            <!-- Footer -->
                            <tr>
                                <td style="background-color: #f8fafc; padding: 20px 32px; border-top: 1px solid #f1f5f9; text-align: center;">
                                    <p style="margin: 0 0 6px 0; font-size: 12px; color: #64748b;">
                                        This is an automated operational notification from <strong>FunMarket Engine</strong>.
                                    </p>
                                    <p style="margin: 0; font-size: 11px; color: #94a3b8;">
                                        Please do not reply directly to this email.
                                    </p>
                                </td>
                            </tr>

                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """.formatted(safeTitle, safeTitle, safeBody);
    }
}