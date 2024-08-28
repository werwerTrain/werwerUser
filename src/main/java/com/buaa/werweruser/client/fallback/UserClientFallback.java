package com.buaa.werweruser.client.fallback;

import com.buaa.werweruser.client.UserClient;
import org.springframework.stereotype.Component;


public class UserClientFallback implements UserClient {

    @Override
    public String getEmail(String userId) {
        // Provide fallback behavior here
        System.out.println("get email request failed, fallback method executed");
        return "fallback@example.com"; // Example fallback value
    }
}
