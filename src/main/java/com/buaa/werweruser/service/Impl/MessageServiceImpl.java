package com.buaa.werweruser.service.Impl;

import com.buaa.werweruser.service.IMessageService;
import com.buaa.werweruser.mapper.IMessageMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 消息
@Service("messageService")
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private IMessageMapper messageMapper;

    @Override
    public void addMessage(String userId, String mid, String orderId, String title, String messageTime, String content,
            Boolean haveRead, String orderType) {
        messageMapper.addMessage(userId, mid, orderId, title, messageTime, content, haveRead, orderType);
    }

    @Override
    // @CircuitBreaker(name = "getMessageCircuitBreaker", fallbackMethod =
    // "getMessageFallback")
    public List<Map<String, Object>> getMessage(String userId) {
        return messageMapper.getMessage(userId);
    }

    @Override
    public void setHaveread(String mid) {
        messageMapper.setHaveread(mid);
    }

    // public List<Map<String, Object>> getMessageFallback(String userId, Throwable
    // t) {
    // List<Map<String, Object>> fallbackList = new ArrayList<>();
    // Map<String, Object> fallbackMap = new HashMap<>();
    // fallbackMap.put("result", null);
    // fallbackList.add(fallbackMap);
    // return fallbackList;
    // }
}
