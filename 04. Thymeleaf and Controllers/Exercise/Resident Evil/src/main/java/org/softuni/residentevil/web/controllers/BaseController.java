package org.softuni.residentevil.web.controllers;

import lombok.extern.java.Log;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

@Log
public class BaseController {

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

    protected static String redirect(String url) {
        return "redirect:" + url;
    }
}
