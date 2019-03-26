package org.softuni.residentevil.domain.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Authority {
    ROOT,
    ADMIN,
    MODERATOR,
    USER;

    private static final Map<String, Authority> LABEL_TO_ENUM_MAP = Stream.of(Authority.values())
            .collect(Collectors.toUnmodifiableMap(Authority::role, authority -> authority));

    private static final String ROLE_PREFIX = "ROLE_";

    private final String role;

    Authority() {
        this.role = ROLE_PREFIX + name();
    }

    public static Authority fromRole(String role) {
        return role == null ? null : LABEL_TO_ENUM_MAP.get(role);
    }

    public String role() {
        return role;
    }
}
