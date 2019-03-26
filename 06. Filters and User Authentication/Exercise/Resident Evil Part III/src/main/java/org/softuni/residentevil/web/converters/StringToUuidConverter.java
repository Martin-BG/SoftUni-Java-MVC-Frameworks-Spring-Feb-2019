package org.softuni.residentevil.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Validated
public class StringToUuidConverter implements Converter<String, UUID> {

    @Override
    public UUID convert(@NotNull String id) {
        return UUID.fromString(id);
    }
}
