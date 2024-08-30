package com.buaa.werweruser.controller;

import com.buaa.werweruser.entity.Message;
import com.buaa.werweruser.service.IMessageService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    private IMessageService messageService;

    public Map<String, Object> getMessageFallback(String userID, Throwable t) {
        System.out.println("调用Fallback函数, 服务降级成功");
        List<Map<String, Object>> fallbackList = new ArrayList<>();

        Map<String, Object> fallbackMap = new HashMap<>();
        fallbackMap.put("orderType", "服务繁忙");
        fallbackMap.put("orderId", "N/A");
        fallbackMap.put("haveRead", Boolean.FALSE);
        fallbackMap.put("title", "服务繁忙");
        LocalDateTime messageTime = LocalDateTime.now();
        String formattedMessageTime = messageTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        fallbackMap.put("messageTime", formattedMessageTime);
        fallbackMap.put("content", "服务繁忙");
        fallbackMap.put("mid", "N/A");

        fallbackList.add(fallbackMap);

        return new HashMap<>() {{
            put("result", fallbackList);
        }};
    }

    @GetMapping("/message/getAll/{userID}")
    @RateLimiter(name = "messageService", fallbackMethod = "getMessageFallback")
    public Map<String, Object> getMessage(@PathVariable("userID") String userID) {
        System.out.println("服务正常");
        List<Map<String, Object>> messageMap = messageService.getMessage(userID);
        List<Object> result = new ArrayList<>();
        for (Map<String, Object> message : messageMap) {
            result.add(new HashMap<>() {{
                put("orderType", message.get("orderType"));
                put("orderId", message.get("orderId"));
                put("haveRead", message.get("haveRead"));
                put("title", message.get("title"));
                LocalDateTime messageTime = (LocalDateTime) message.get("messageTime");
                String formattedMessageTime = messageTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                put("messageTime", formattedMessageTime);
                put("content", message.get("content"));
                put("mid", message.get("mid"));
            }});
        }
        // System.out.println(result.size());
        return new HashMap<>() {{
            put("result", result);
        }};
    }


    @PostMapping("/message/setRead/{mid}")
    public void setHaveread(@PathVariable String mid) {
        // 标记已读
        messageService.setHaveread(mid);
    }

    @PostMapping("/message/setAllRead/{userId}")
    public void setAllRead(@PathVariable String userId) {
        List<Map<String, Object>> messageMap = messageService.getMessage(userId);
        for (Map<String, Object> message : messageMap) {
            messageService.setHaveread(message.get("mid").toString());
        }
    }

    @PostMapping("/addMessage")
    @CircuitBreaker(name="addMessage",fallbackMethod = "addMessageFallback")
    public void addMessage(@RequestBody Map<String, Object> messageMap) {
        String userId = messageMap.get("userId").toString();
        String mid = Message.generateMessageId();
        String orderId = messageMap.get("orderId").toString();
        String title = messageMap.get("title").toString();
        String messageTime = messageMap.get("messageTime").toString();
        String content = messageMap.get("content").toString();
        String orderType = messageMap.get("orderType").toString();
        messageService.addMessage(userId, mid, orderId, title, messageTime, content, false, orderType);
    }

    public void addMessageFallback(@RequestBody Map<String, Object> messageMap, Throwable t){
        System.out.println("addMessage request failed, fallback method executed.");
    }
}

