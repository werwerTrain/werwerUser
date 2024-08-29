package com.buaa.werweruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WerwerUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(WerwerUserApplication.class, args);
    }
}
