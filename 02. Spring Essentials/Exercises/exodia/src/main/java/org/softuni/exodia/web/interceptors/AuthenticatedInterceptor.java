package org.softuni.exodia.web.interceptors;

import org.softuni.exodia.annotations.AuthenticatedUser;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Grant access or redirect to another URL depending {@link AuthenticatedUser}
 * annotation and presence of required session attribute.
 * <ul>Default values (can be changed during interceptor's instance build):
 * <li>Authenticated user URL: {@value #DEFAULT_AUTHENTICATED_REDIRECT_URL}</li>
 * <li>Guest user URL: {@value #DEFAULT_GUEST_REDIRECT_URL}</li>
 * <li>Session attribute name: {@value #SESSION_ATTRIBUTE}</li>
 * </ul>
 */

public final class AuthenticatedInterceptor extends HandlerInterceptorAdapter {

    private static final String SESSION_ATTRIBUTE = "username";
    private static final String DEFAULT_AUTHENTICATED_REDIRECT_URL = "/";
    private static final String DEFAULT_GUEST_REDIRECT_URL = "/login";

    private String sessionAttribute;
    private String authenticatedRedirectUrl;
    private String guestRedirectUrl;

    private AuthenticatedInterceptor() {
        sessionAttribute = SESSION_ATTRIBUTE;
        authenticatedRedirectUrl = DEFAULT_AUTHENTICATED_REDIRECT_URL;
        guestRedirectUrl = DEFAULT_GUEST_REDIRECT_URL;
    }

    private static boolean isResourceHandler(Object handler) {
        return handler instanceof ResourceHttpRequestHandler;
    }

    private static AuthenticatedUser getMethodOrTypeAnnotation(HandlerMethod handlerMethod) {
        AuthenticatedUser authenticatedUser = handlerMethod.getMethodAnnotation(AuthenticatedUser.class);
        if (authenticatedUser == null) {
            return handlerMethod.getBeanType().getAnnotation(AuthenticatedUser.class);
        }
        return authenticatedUser;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!isResourceHandler(handler)) {
            AuthenticatedUser authenticatedUser = getMethodOrTypeAnnotation((HandlerMethod) handler);

            if (authenticatedUser != null) {
                boolean shouldBeAuthenticated = authenticatedUser.value();
                boolean isAuthenticated = isAuthenticated(request.getSession(), authenticatedUser.sessionAttribute());

                if (shouldBeAuthenticated != isAuthenticated) {
                    response.sendRedirect(getRedirectUrl(isAuthenticated, authenticatedUser.url()));
                    return false;
                }
            }
        }

        return super.preHandle(request, response, handler);
    }

    private boolean isAuthenticated(HttpSession session, String attributeName) {
        if (AuthenticatedUser.USE_DEFAULT_SESSION_ATTRIBUTE_NAME.equals(attributeName)) {
            attributeName = sessionAttribute;
        }
        return session.getAttribute(attributeName) != null;
    }

    private String getRedirectUrl(boolean isAuthenticated, String redirectUrl) {
        if (redirectUrl != null && !AuthenticatedUser.USE_DEFAULT_REDIRECT_URL.equals(redirectUrl)) {
            return redirectUrl;
        }
        return isAuthenticated ? authenticatedRedirectUrl : guestRedirectUrl;
    }

    public static final class Builder {
        private AuthenticatedInterceptor toBuild;

        private Builder() {
            toBuild = new AuthenticatedInterceptor();
        }

        public Builder withSessionAttributeName(String sessionAttribute) {
            toBuild.sessionAttribute = sessionAttribute;
            return this;
        }

        public Builder withAuthenticatedRedirectUrl(String authenticatedRedirectUrl) {
            toBuild.authenticatedRedirectUrl = authenticatedRedirectUrl;
            return this;
        }

        public Builder withGuestRedirectUrl(String guestRedirectUrl) {

            toBuild.guestRedirectUrl = guestRedirectUrl;
            return this;
        }

        public AuthenticatedInterceptor build() {
            Assert.notNull(toBuild, "Reuse of builder is not allowed");
            Assert.hasLength(toBuild.sessionAttribute, "Session attribute name cannot be empty or null");
            Assert.hasLength(toBuild.authenticatedRedirectUrl, "Users redirect URL cannot be empty or null");
            Assert.hasLength(toBuild.guestRedirectUrl, "Guests redirect URL cannot be empty or null");

            AuthenticatedInterceptor built = toBuild;
            toBuild = null;
            return built;
        }
    }
}
