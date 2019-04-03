package org.softuni.residentevil.domain.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Authority {
    ROOT,
    ADMIN,
    MODERATOR,
    USER;

    private static final Map<String, Authority> STRING_TO_ENUM = Stream.of(Authority.values())
            .collect(Collectors.toUnmodifiableMap(Authority::role, authority -> authority));

    private static final String ROLE_PREFIX = "ROLE_";

    private final String role;

    Authority() {
        this.role = ROLE_PREFIX + name();
    }

    public static Authority fromRole(String role) {
        return role == null ? null : STRING_TO_ENUM.get(role);
    }

    public String role() {
        return role;
    }
}
