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
 * Enforced by {@link AuthenticatedInterceptor}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AuthenticatedUser {

    boolean authenticated() default true;
}
