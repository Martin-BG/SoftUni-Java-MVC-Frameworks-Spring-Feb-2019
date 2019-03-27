package org.softuni.residentevil.web.handlers;

import lombok.extern.java.Log;
import org.softuni.residentevil.config.WebConfig;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

@Log
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            log.log(Level.INFO, String.format(
                    "User: %s attempted to access the protected URL: %s",
                    auth.getName(), request.getRequestURI()));
        }

        response.sendRedirect(request.getContextPath() + WebConfig.URL_ERROR_UNAUTHORIZED);
    }
}
