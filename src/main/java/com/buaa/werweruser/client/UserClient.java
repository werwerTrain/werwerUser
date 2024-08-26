package com.buaa.werweruser.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",contextId = "userClient")
public interface UserClient {

    @GetMapping("/getEmail/{userId}")
    public String getEmail(@PathVariable("userId") String userId);
}
