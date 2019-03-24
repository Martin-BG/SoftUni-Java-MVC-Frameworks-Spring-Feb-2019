package com.minkov.springintroapp.services.implementations;

import com.minkov.springintroapp.services.CarsFormatterService;
import org.springframework.stereotype.Service;

@Service
public class CarsFormatterServiceImpl implements CarsFormatterService {
    public static final String CAR_CREATED_EMAIL_BODY_TEMPLATE = "Success, you created %s.";

    @Override
    public String getCarCreatedEmailBody(String model) {
        return String.format(CAR_CREATED_EMAIL_BODY_TEMPLATE, model);
    }
}
