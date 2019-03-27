package org.softuni.residentevil.web.controllers.error;

import org.softuni.residentevil.config.WebConfig;
import org.softuni.residentevil.web.annotations.Layout;
import org.softuni.residentevil.web.controllers.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Layout
@Controller
public class UnauthorizedController extends BaseController {

    private static final String VIEW_ERROR_UNAUTHORIZED = "error/unauthorized";

    @GetMapping(WebConfig.URL_ERROR_UNAUTHORIZED)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String get() {
        return VIEW_ERROR_UNAUTHORIZED;
    }
}
