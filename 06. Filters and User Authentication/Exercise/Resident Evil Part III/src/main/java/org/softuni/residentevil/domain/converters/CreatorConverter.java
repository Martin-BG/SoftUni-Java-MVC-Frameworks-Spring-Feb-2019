package org.softuni.residentevil.domain.converters;

import org.softuni.residentevil.domain.enums.Creator;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CreatorConverter implements AttributeConverter<Creator, String> {

    @Override
    public String convertToDatabaseColumn(Creator creator) {
        return creator == null ? null : creator.getLabel();
    }

    @Override
    public Creator convertToEntityAttribute(String label) {
        return Creator.fromLabel(label);
    }
}
