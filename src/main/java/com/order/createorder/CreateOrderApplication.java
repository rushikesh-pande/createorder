package com.order.createorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for Create Order Service
 * Integrated with User Authentication Library
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.order.createorder", "com.auth.userauth"})
public class CreateOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreateOrderApplication.class, args);
        System.out.println("Create Order Service started with User Authentication support");
        System.out.println("Default users: admin (admin123), user (user123)");
    }
}

