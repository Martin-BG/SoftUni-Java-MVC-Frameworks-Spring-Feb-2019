package org.softuni.residentevil.config;

import org.softuni.residentevil.web.converters.CreatorToStringConverter;
import org.softuni.residentevil.web.converters.StringToCapitalBindingModelConverter;
import org.softuni.residentevil.web.converters.StringToCreatorConverter;
import org.softuni.residentevil.web.converters.StringToUuidConverter;
import org.softuni.residentevil.web.interceptors.ThymeleafLayoutInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String URL_INDEX = "/";
    public static final String URL_VIRUS_ALL = "/viruses";
    public static final String URL_VIRUS_ADD = "/viruses/add";
    public static final String URL_VIRUS_DELETE = "/viruses/delete";
    public static final String URL_VIRUS_EDIT = "/viruses/edit";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                ThymeleafLayoutInterceptor
                        .builder()
                        .withDefaultLayout("/layouts/default")
                        .withViewAttribute("view")
                        .withViewPrefix("/views/")
                        .build());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCreatorConverter());
        registry.addConverter(new CreatorToStringConverter());
        registry.addConverter(new StringToCapitalBindingModelConverter());
        registry.addConverter(new StringToUuidConverter());
    }
}
