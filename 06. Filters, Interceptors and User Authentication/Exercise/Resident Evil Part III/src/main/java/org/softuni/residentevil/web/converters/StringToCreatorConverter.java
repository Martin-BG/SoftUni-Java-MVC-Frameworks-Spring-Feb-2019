package org.softuni.residentevil.web.converters;

import org.softuni.residentevil.domain.enums.Creator;
import org.springframework.core.convert.converter.Converter;

public class StringToCreatorConverter implements Converter<String, Creator> {

    @Override
    public Creator convert(String label) {
        return Creator.fromLabel(label);
    }
}
