package com.minkov.springintroapp.services;

public interface EmailsService {
    void send(String email, String subject, String message);
}
