package org.softuni.exodia.web.interceptors;

import org.softuni.exodia.annotations.AuthenticatedUser;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Grant access or redirect to {@link #URL_INDEX} or {@link #URL_LOGIN} depending {@link AuthenticatedUser}
 * annotation and {@link #SESSION_ATTRIBUTE} session attribute.
 */
@Component
public class AuthenticatedInterceptor extends HandlerInterceptorAdapter {

    private static final String SESSION_ATTRIBUTE = "username";
    private static final String URL_INDEX = "/";
    private static final String URL_LOGIN = "/login";

    private static boolean isResourceHandler(Object handler) {
        return handler instanceof ResourceHttpRequestHandler;
    }

    private static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(SESSION_ATTRIBUTE) != null;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler != null && !isResourceHandler(handler)) {
            HandlerMethod actionHandler = (HandlerMethod) handler;
            AuthenticatedUser annotation = actionHandler.getMethodAnnotation(AuthenticatedUser.class);

            if (annotation == null) { // Using of class annotation if method annotation is not present
                annotation = actionHandler.getBeanType().getAnnotation(AuthenticatedUser.class);
            }

            if (annotation != null) {
                boolean shouldBeAuthenticated = annotation.authenticated();
                boolean isLoggedIn = isLoggedIn(request.getSession());

                if (shouldBeAuthenticated != isLoggedIn) {
                    response.sendRedirect(isLoggedIn ? URL_INDEX : URL_LOGIN);
                    return false;
                }
            }
        }

        return super.preHandle(request, response, handler);
    }
}
