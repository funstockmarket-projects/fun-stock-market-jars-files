package com.fsm.dominsMapping.constantsBO;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum MarketEventsBO {

    WEEKLY("weekly"),
    MONTHLY("monthly"),
    DAILY("daily"),
    YEARLY("yearly");

    private final String eventName;

    MarketEventsBO(String eventName) {
        this.eventName = eventName;
    }


    public static boolean containsEventName(String name) {
        if (name == null) return false;
        return Arrays.stream(values()).anyMatch(e -> e.eventName.equalsIgnoreCase(name));
    }

    public static Optional<MarketEventsBO> fromEventName(String name) {
        if (name == null) return Optional.empty();
        return Arrays.stream(values())
                .filter(e -> e.eventName.equalsIgnoreCase(name))
                .findFirst();
    }

    public static boolean containsEnumName(String name) {
        if (name == null) return false;
        try {
            MarketEventsBO.valueOf(name.toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
