package com.minkov.springintroapp.providers.implementations;

import com.minkov.springintroapp.providers.RandomProvider;
import org.springframework.stereotype.Component;

@Component
public class RandomProviderImpl implements RandomProvider {
    private static RandomProviderImpl instance;

    @Override
    public int getRandomInt() {
        return (int) (Math.random() * 1000);
    }
}
