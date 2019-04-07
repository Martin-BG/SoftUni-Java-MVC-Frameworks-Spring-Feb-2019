package org.softuni.residentevil.web.converters;

import org.softuni.residentevil.domain.enums.Creator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StringToCreatorConverter implements Converter<String, Creator> {

    @Override
    public Creator convert(String label) {
        return Creator.fromLabel(Objects.requireNonNull(label));
    }
}
