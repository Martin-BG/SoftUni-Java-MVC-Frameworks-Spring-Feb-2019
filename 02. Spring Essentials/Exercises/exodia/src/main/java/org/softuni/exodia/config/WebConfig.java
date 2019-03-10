package org.softuni.exodia.config;

import org.softuni.exodia.web.interceptors.AuthenticatedInterceptor;
import org.softuni.exodia.web.interceptors.ThymeleafLayoutInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String SESSION_ATTRIBUTE_USERNAME = "username";

    public static final String URL_INDEX = "/";
    public static final String URL_LOGIN = "/login";
    public static final String URL_LOGOUT = "/logout";
    public static final String URL_REGISTER = "/register";
    public static final String URL_SCHEDULE = "/schedule";
    public static final String URL_DETAILS = "/details";
    public static final String URL_PRINT = "/print";
    public static final String URL_DOWNLOAD = "/download";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                AuthenticatedInterceptor
                        .builder()
                        .withSessionAttributeName(SESSION_ATTRIBUTE_USERNAME)
                        .withAuthenticatedRedirectUrl(URL_INDEX)
                        .withGuestRedirectUrl(URL_LOGIN)
                        .build());

        registry.addInterceptor(
                ThymeleafLayoutInterceptor
                        .builder()
                        .withDefaultLayout("/layouts/default")
                        .withViewAttribute("view")
                        .withViewPrefix("/views/")
                        .build());
    }
}
