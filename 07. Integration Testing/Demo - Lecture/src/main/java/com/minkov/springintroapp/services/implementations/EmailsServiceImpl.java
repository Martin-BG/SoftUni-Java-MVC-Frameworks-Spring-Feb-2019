package com.minkov.springintroapp.services.implementations;

import com.minkov.springintroapp.services.EmailsService;
import org.springframework.stereotype.Service;

@Service
public class EmailsServiceImpl implements EmailsService {
    private static final String emailTemplate =
            "=============================================%n" +
            "to %s%n" +
            "---------------------------------------------%n" +
            "Subject %s%n" +
            "---------------------------------------------%n" +
            "%s%n" +
            "=============================================%n";

    @Override
    public void send(String email, String subject, String message) {
        System.out.printf(emailTemplate, email, subject, message);
    }
}
