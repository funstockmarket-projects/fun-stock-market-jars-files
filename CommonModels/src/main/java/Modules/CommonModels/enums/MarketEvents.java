package Modules.CommonModels.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum MarketEvents {

    WEEKLY("weekly"),
    MONTHLY("monthly"),
    DAILY("daily"),
    YEARLY("yearly");

    private final String eventName;

    MarketEvents(String eventName) {
        this.eventName = eventName;
    }

}
