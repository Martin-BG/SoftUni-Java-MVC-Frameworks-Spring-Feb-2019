package org.softuni.exodia.annotations;

import org.softuni.exodia.web.interceptors.ThymeleafLayoutInterceptor;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Defines Thymeleaf layout template to render annotated view to.
 * <p>
 * Can be placed on Controller class or methods (higher priority).
 * <p>
 * value is optional and overrides default template set in {@link ThymeleafLayoutInterceptor}
 * <p>
 * Set value to {@value #NONE} to prevent modifications by {@link ThymeleafLayoutInterceptor}
 * <hr>
 * Sample layout (resources/templates/layouts/default.html):
 * <pre>{@code <!DOCTYPE html>
 * <html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
 * <head>...</head>
 * <body>
 *     <header>...</header>
 *     <main th:insert="${view}"></main>
 *     <footer>...</footer>
 * </body>
 * </html>
 * }</pre>
 * Sample view (resources/templates/views/hello.html):
 * <pre>{@code <th:block xmlns:th="http://www.thymeleaf.org">
 *     <h1>Hello World!</h1>
 * </th:block>
 * }</pre>
 * Sample controller method:
 * <pre>{@code     @Layout
 *     @GetMapping("/hello")
 *     public String hello() {
 *         return "hello";
 *     }}</pre>
 * <p>
 * <hr>
 * Inspired by kolorobot's <a href="https://github.com/kolorobot/thymeleaf-custom-layout">Thymeleaf Custom Layout</a>
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Layout {
    String NONE = "none"; // no layout will be used

    @AliasFor("layout")
    String value() default "";

    @AliasFor("value")
    String layout() default "";
}
