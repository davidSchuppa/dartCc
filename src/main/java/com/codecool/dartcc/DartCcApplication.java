package com.codecool.dartcc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DartCcApplication {

    public static void main(String[] args) {
        SpringApplication.run(DartCcApplication.class, args);
    }

}
