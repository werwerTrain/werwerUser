package com.buaa.werweruser.client.fallback;

import com.buaa.werweruser.client.MessageClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class MessageClientFallback implements MessageClient {

    @Override
    public void addMessage(@RequestBody Map<String, Object> messageMap) {
        System.out.println("addMessage request failed, fallback method executed.");
    }
}
