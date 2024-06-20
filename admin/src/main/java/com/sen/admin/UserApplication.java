package com.sen.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-20
 */
@SpringBootApplication(scanBasePackages = {"com.sen"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
