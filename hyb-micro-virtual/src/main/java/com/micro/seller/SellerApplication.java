package com.micro.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = {"com.micro.seller"})
@EnableCaching
public class SellerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SellerApplication.class, args);
    }
}
