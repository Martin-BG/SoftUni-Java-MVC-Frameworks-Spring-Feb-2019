package org.softuni.residentevil.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Magnitude {

    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private static final Map<String, Magnitude> LABEL_TO_ENUM_MAP = Stream.of(Magnitude.values())
            .collect(Collectors.toUnmodifiableMap(Magnitude::getLabel, sector -> sector));

    private final String label;

    Magnitude(String label) {
        this.label = label;
    }

    public static Magnitude fromLabel(String label) {
        return label == null ? null : LABEL_TO_ENUM_MAP.get(label);
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
