package com.buaa.werweruser.service.Impl;

import com.buaa.werweruser.service.IMessageService;
import com.buaa.werweruser.mapper.IMessageMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 消息
@Service("messageService")
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private IMessageMapper messageMapper;

    @Override
    public void addMessage(String userId, String mid, String orderId, String title, String messageTime, String content, Boolean haveRead, String orderType) {
        messageMapper.addMessage(userId, mid, orderId, title, messageTime, content, haveRead, orderType);
    }

    @Override
    public List<Map<String, Object>> getMessage(String userId) {
        return messageMapper.getMessage(userId);
    }


    @Override
    public void setHaveread(String mid) {
        messageMapper.setHaveread(mid);
    }


}