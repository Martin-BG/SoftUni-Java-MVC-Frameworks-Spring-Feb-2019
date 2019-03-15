package org.softuni.residentevil.domain.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Creator {

    CORP_TITLE("Corp"),
    CORP_LOWER("corp");

    private static final Map<String, Creator> LABEL_TO_ENUM_MAP = Stream.of(Creator.values())
            .collect(Collectors.toUnmodifiableMap(Creator::getLabel, creator -> creator));

    private final String label;

    Creator(String label) {
        this.label = label;
    }

    public static Creator fromLabel(String label) {
        return label == null ? null : LABEL_TO_ENUM_MAP.get(label);
    }

    public String getLabel() {
        return label;
    }
}
