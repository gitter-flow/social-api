package com.gitter.socialapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class SocialApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialApiApplication.class, args);
    }
}
