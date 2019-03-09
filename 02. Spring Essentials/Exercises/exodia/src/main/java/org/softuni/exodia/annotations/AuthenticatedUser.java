package org.softuni.exodia.annotations;

import org.softuni.exodia.web.interceptors.AuthenticatedInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Grants or disallows access to Controller methods.
 * <p>
 * Can be placed on Controller class or methods (higher priority).
 * <p>
 * url is optional and defines the URL to redirect invalid requests to
 * otherwise, URLs defined by {@link AuthenticatedInterceptor} will be used
 * <p>
 * sessionAttribute is optional and allows to check for another Session Attribute,
 * instead of the default one defined by {@link AuthenticatedInterceptor}
 * <p>
 * Enforced by {@link AuthenticatedInterceptor}
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AuthenticatedUser {
    String USE_DEFAULT_REDIRECT_URL = "";
    String USE_DEFAULT_SESSION_ATTRIBUTE_NAME = "";

    boolean authenticated() default true;

    String url() default USE_DEFAULT_REDIRECT_URL;

    String sessionAttribute() default USE_DEFAULT_SESSION_ATTRIBUTE_NAME;
}
