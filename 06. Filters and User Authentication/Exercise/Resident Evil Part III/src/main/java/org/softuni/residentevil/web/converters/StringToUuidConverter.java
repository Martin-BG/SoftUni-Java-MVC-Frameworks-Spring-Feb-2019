package org.softuni.residentevil.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class StringToUuidConverter implements Converter<String, UUID> {

    @Override
    public UUID convert(String id) {
        return UUID.fromString(Objects.requireNonNull(id));
    }
}
