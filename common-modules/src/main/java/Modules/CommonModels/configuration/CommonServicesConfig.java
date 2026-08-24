package Modules.CommonModels.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class CommonServicesConfig {

    @Value("${spring.mail.host:smtp.gmail.com}")
    private String mailHost;

    @Value("${spring.mail.port:587}")
    private int mailPort;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    @Value("${spring.mail.password:}")
    private String mailPassword;

    @Value("${spring.mail.from:}")
    private String mailFrom;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setProtocol("smtp");

        // Ensure we have a from address; prefer explicit mail.from, fallback to username
        if ((mailFrom == null || mailFrom.isBlank()) && mailUsername != null && !mailUsername.isBlank()) {
            mailFrom = mailUsername;
        }

        if (mailUsername != null && !mailUsername.isBlank()) {
            mailSender.setUsername(mailUsername);
        }
        if (mailPassword != null && !mailPassword.isBlank()) {
            mailSender.setPassword(mailPassword);
        }
        mailSender.setDefaultEncoding("UTF-8");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.mime.charset", "UTF-8");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.ssl.trust", mailHost);

        // Set the SMTP envelope from to control Return-Path; helps DMARC/SPF alignment
        if (mailFrom != null && !mailFrom.isBlank()) {
            props.put("mail.smtp.from", mailFrom);
        }

        // For implicit SSL port, enable ssl and disable STARTTLS
        if (mailPort == 465) {
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.starttls.enable", "false");
        }

        // Optional: enable debug via property (disabled by default)
        // props.put("mail.debug", "true");

        return mailSender;
    }
}
