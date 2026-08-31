package Modules.notifications.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.Set;

@Getter
@Builder
@ToString
public class MessagePayload {
    private String subject;
    private String from;
    private String notificationBody;

    @Builder.Default
    private final boolean isHTML = false;

    @Singular("to")
    private Set<String> triggerAddress;

    @Singular("cc")
    private final Set<String> cc;

    @Singular("bcc")
    private final Set<String> bcc;
}
