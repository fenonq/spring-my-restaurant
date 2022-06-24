package com.fenonq.spring.myrestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SpringMyRestaurantApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMyRestaurantApplication.class, args);
    }

}
