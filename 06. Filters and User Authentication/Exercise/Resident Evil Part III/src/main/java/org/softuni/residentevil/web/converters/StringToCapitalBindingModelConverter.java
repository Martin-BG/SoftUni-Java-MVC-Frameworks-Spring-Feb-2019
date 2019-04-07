package org.softuni.residentevil.web.converters;

import org.softuni.residentevil.domain.models.binding.capital.CapitalBindingModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StringToCapitalBindingModelConverter implements Converter<String, CapitalBindingModel> {

    @Override
    public CapitalBindingModel convert(String id) {
        CapitalBindingModel capitalBindingModel = new CapitalBindingModel();
        capitalBindingModel.setId(Long.parseLong(Objects.requireNonNull(id)));
        return capitalBindingModel;
    }
}
