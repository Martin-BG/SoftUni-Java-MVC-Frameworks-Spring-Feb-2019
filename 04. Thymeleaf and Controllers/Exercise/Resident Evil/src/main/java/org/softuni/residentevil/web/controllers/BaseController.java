package org.softuni.residentevil.web.controllers;

import lombok.extern.java.Log;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.List;
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

    /**
     * Prevent text modification (like trimming) of specified input fields,
     * for example "password", "repeatPassword" etc.
     *
     * @param binder              {@link WebDataBinder}
     * @param doNotTrimFieldsList List of field names that should be read without modifications
     */
    private static void preventTextModificationForFields(WebDataBinder binder, List<String> doNotTrimFieldsList) {
        PropertyEditorSupport noTrimPropertyEditor = new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                super.setValue(text);
            }
        };

        doNotTrimFieldsList.forEach(field ->
                binder.registerCustomEditor(String.class, field, noTrimPropertyEditor));
    }

    /**
     * Define modification rules for input fields<br>
     * Settings are inherited by extending classes
     *
     * @param binder {@link WebDataBinder}
     */
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        // Trim form input texts, empty strings are NOT treated as null
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));

        List<String> doNotTrimFieldsList = List.of(); // field names, ex. "password"
        preventTextModificationForFields(binder, doNotTrimFieldsList);
    }
}
