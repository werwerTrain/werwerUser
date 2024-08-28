package com.buaa.werweruser.controller;

import com.buaa.werweruser.entity.Message;
import com.buaa.werweruser.service.IMessageService;
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

    @GetMapping("/message/getAll/{userID}")
    public Map<String, Object> getMessage(@PathVariable String userID) {
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
    public void addMessage(@RequestBody Map<String, Object> messageMap) {
//        @RequestParam String userId,
//        @RequestParam String mid,
//        @RequestParam String orderId,
//        @RequestParam String title,
//        @RequestParam String messageTime,
//        @RequestParam String content,
//        @RequestParam Boolean haveRead,
//        @RequestParam String orderType
        String userId = messageMap.get("userId").toString();
        String mid = Message.generateMessageId();
        String orderId = messageMap.get("orderId").toString();
        String title = messageMap.get("title").toString();
        String messageTime = messageMap.get("messageTime").toString();
        String content = messageMap.get("content").toString();
        String orderType = messageMap.get("orderType").toString();
        messageService.addMessage(userId, mid, orderId, title, messageTime, content, false, orderType);
    }
}

