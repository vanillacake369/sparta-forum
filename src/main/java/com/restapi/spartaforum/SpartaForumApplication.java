package com.restapi.spartaforum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpartaForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartaForumApplication.class, args);
    }

}
