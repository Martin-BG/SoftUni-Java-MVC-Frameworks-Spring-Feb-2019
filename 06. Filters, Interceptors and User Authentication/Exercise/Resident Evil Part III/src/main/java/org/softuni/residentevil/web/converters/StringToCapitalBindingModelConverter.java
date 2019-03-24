package org.softuni.residentevil.web.converters;

import org.softuni.residentevil.domain.models.binding.capital.CapitalBindingModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public class StringToCapitalBindingModelConverter implements Converter<String, CapitalBindingModel> {

    @Override
    public CapitalBindingModel convert(@NotNull String id) {
        CapitalBindingModel capitalBindingModel = new CapitalBindingModel();
        capitalBindingModel.setId(Long.parseLong(id));
        return capitalBindingModel;
    }
}
