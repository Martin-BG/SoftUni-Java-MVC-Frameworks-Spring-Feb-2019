package org.softuni.residentevil.domain.converters;

import org.softuni.residentevil.domain.enums.Authority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AuthorityConverter implements AttributeConverter<Authority, String> {

    @Override
    public String convertToDatabaseColumn(Authority authority) {
        return authority == null ? null : authority.role();
    }

    @Override
    public Authority convertToEntityAttribute(String role) {
        return Authority.fromRole(role);
    }
}
