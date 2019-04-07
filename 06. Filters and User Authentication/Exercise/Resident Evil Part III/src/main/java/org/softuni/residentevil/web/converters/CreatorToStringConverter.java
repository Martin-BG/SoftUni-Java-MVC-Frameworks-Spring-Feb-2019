package org.softuni.residentevil.web.converters;

import org.softuni.residentevil.domain.enums.Creator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreatorToStringConverter implements Converter<Creator, String> {

    @Override
    public String convert(Creator creator) {
        return Objects.requireNonNull(creator).getLabel();
    }
}
