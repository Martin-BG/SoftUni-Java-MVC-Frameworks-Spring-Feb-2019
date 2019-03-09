package org.softuni.exodia.web.controllers;

import lombok.extern.java.Log;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

@Log
class BaseController {

    protected BaseController() {
    }

    protected static Optional<UUID> uuid(String id) {
        try {
            return Optional.of(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE, "String cannot be converted to UUID: " + id, e);
            return Optional.empty();
        }
    }

    protected String buildView(String view, Model model) {
        model.addAttribute("view", "views/" + view + ".html");
        return "layouts/default";
    }

    protected String redirect(String url) {
        return "redirect:" + url;
    }
}
