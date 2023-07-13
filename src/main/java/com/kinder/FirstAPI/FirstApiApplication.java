package com.kinder.FirstAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class FirstApiApplication {

    public static Logger LOG = Logger.getLogger("DEBUG");

    public static void main(String[] args) {
        SpringApplication.run(FirstApiApplication.class, args);
    }
}
