package org.softuni.residentevil;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.logging.Level;

@Log
@SpringBootApplication
public class ResidentEvilApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ResidentEvilApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ResidentEvilApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        log.log(Level.INFO, "Application deployed and ready to use");
    }
}
